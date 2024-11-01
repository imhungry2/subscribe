package com.fastcampus.netplix.movie;

import com.fastcampus.netplix.movie.response.LikeMovieUseCase;
import com.fastcampus.netplix.movie.response.MovieResponse;
import com.fastcampus.netplix.movie.response.PageableMoviesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService implements FetchMovieUseCase, InsertMovieUseCase, DownloadMovieUseCase, LikeMovieUseCase {

    private final TmdbMoviePort tmdbMoviePort;
    private final PersistenceMoviePort persistenceMoviePort;
    private final DownloadMoviePort downloadMoviePort;
    private final LikeMoviePort likeMoviePort;

    private final List<UserDownloadMovieRoleValidator> validators;

    @Override
    public PageableMoviesResponse fetchFromClien(int page) {
        TmdbPageableMovies tmdbPageableMovies = tmdbMoviePort.fetchPageable(page);

        return new PageableMoviesResponse(
                tmdbPageableMovies.getTmdbMovies().stream()
                        .map(movie -> new MovieResponse(
                                        movie.getMovieName(),
                                        movie.isAdult(),
                                        movie.getGenre(),
                                        movie.getOverview(),
                                        movie.getReleaseAt()
                                )
                        ).collect(Collectors.toList()),
                tmdbPageableMovies.getPage(),
                tmdbPageableMovies.isHasNext()
        );
    }

    @Override
    public PageableMoviesResponse fetchFromDb(int page) {
        List<NetplixMovie> netplixMovies = persistenceMoviePort.fetchBy(page, 10);

        return new PageableMoviesResponse(
                netplixMovies.stream().map(it -> new MovieResponse(it.getMovieName(), it.isAdult(), List.of(), it.getOverview(), it.getReleaseAt())).toList(),
                page,
                true
                );
    }

    @Override
    public void insert(List<MovieResponse> items) {
        items.forEach(it -> {
            NetplixMovie netplixMovie = NetplixMovie.builder()
                    .movieName(it.getMovieName())
                    .isAdult(it.isAdult())
                    .overview(it.getOverview())
                    .genre("genre")
                    .releaseAt(it.getReleaseAt())
                    .build();
            persistenceMoviePort.insert(netplixMovie);
        });
    }

    @Override
    public String download(String userId, String role, String movieId) {
        long count = downloadMoviePort.downloadCountToday(userId);
        boolean validate = validators.stream()
                .filter(validators -> validators.isTarget(role))
                .findAny()
                .orElseThrow()
                .validate(count);

        if (!validate) {
            throw new RuntimeException("더 이상 다운로드 할 수 없습니다.");
        }

        NetplixMovie by = persistenceMoviePort.findBy(movieId);

        downloadMoviePort.save(UserMovieDownload.newDownload(userId, movieId));

        return by.getMovieName();
    }

    @Override
    public Boolean like(String userId, String movieId) {
        Optional<UserMovieLike> byUserIdAndMovieId = likeMoviePort.findByUserIdAndMovieId(userId, movieId);
        UserMovieLike userMovieLike = byUserIdAndMovieId
                .orElseGet(() -> likeMoviePort.save(UserMovieLike.newLike(userId, movieId)));

        userMovieLike.like();
        likeMoviePort.save(userMovieLike);

        return true;
    }

    @Override
    public Boolean unlike(String userId, String movieId) {
        Optional<UserMovieLike> byUserIdAndMovieId = likeMoviePort.findByUserIdAndMovieId(userId, movieId);
        UserMovieLike userMovieLike = byUserIdAndMovieId
                .orElseGet(() -> likeMoviePort.save(UserMovieLike.newLike(userId, movieId)));

        userMovieLike.unlike();
        likeMoviePort.save(userMovieLike);

        return true;
    }
}
