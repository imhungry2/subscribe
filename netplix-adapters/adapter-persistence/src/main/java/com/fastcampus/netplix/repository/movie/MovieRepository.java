package com.fastcampus.netplix.repository.movie;

import com.fastcampus.netplix.entity.movie.MovieEntity;
import com.fastcampus.netplix.movie.NetplixMovie;
import com.fastcampus.netplix.movie.PersistenceMoviePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class MovieRepository implements PersistenceMoviePort {

    private final MovieJpaRepository movieJpaRepository;

    @Override
    public List<NetplixMovie> fetchBy(int page, int size) {
        return movieJpaRepository.search(PageRequest.of(page, size))
                .stream().map(MovieEntity::toDomain)
                .toList();
    }

    @Override
    public NetplixMovie findBy(String movieName) {
        return movieJpaRepository.findByMovieName(movieName)
                .map(MovieEntity::toDomain)
                .orElseThrow();
    }

    @Override
    public void insert(NetplixMovie netplixMovie) {
        Optional<MovieEntity> byMovieName = movieJpaRepository.findByMovieName(netplixMovie.getMovieName());

        if (byMovieName.isPresent()) {
            return;
        }

        MovieEntity movieEntity = MovieEntity.newMovieEntity(
                netplixMovie.getMovieName(),
                netplixMovie.isAdult(),
                netplixMovie.getGenre(),
                netplixMovie.getOverview(),
                netplixMovie.getReleaseAt()
        );

        movieJpaRepository.save(movieEntity);
    }
}
