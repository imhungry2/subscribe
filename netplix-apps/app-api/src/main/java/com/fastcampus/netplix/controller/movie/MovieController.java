package com.fastcampus.netplix.controller.movie;

import com.fastcampus.netplix.controller.NetplixApiResponse;
import com.fastcampus.netplix.filter.JwtTokenProvider;
import com.fastcampus.netplix.movie.DownloadMovieUseCase;
import com.fastcampus.netplix.movie.FetchMovieUseCase;
import com.fastcampus.netplix.movie.response.LikeMovieUseCase;
import com.fastcampus.netplix.movie.response.PageableMoviesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final FetchMovieUseCase fetchMovieUseCase;
    private final DownloadMovieUseCase downloadMovieUseCase;
    private final LikeMovieUseCase likeMovieUseCase;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/v1/movie/client/{page}")
    public NetplixApiResponse<PageableMoviesResponse> fetchMoviePageable(@PathVariable int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUseCase.fetchFromClien(page);
        return NetplixApiResponse.ok(pageableMoviesResponse);
    }

    @PostMapping("/api/v1/movie/search")
    public NetplixApiResponse<PageableMoviesResponse> search(@RequestParam int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUseCase.fetchFromDb(page);
        return NetplixApiResponse.ok(pageableMoviesResponse);
    }

    @PostMapping("/api/v1/movie/{movieId}/download")
    @PreAuthorize("hasAnyRole('ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetplixApiResponse<String> download(@PathVariable String movieId) {
        String download = downloadMovieUseCase.download(jwtTokenProvider.getUserId(), jwtTokenProvider.getRole(), movieId);
        return NetplixApiResponse.ok(download);
    }

    @PostMapping("/api/v1/movie/{movieId}/like")
    @PreAuthorize("hasAnyRole('ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetplixApiResponse<Boolean> likeMovie(@PathVariable String movieId) {
        return NetplixApiResponse.ok(likeMovieUseCase.like(jwtTokenProvider.getUserId(), movieId));
    }

    @PostMapping("/api/v1/movie/{movieId}/unlike")
    @PreAuthorize("hasAnyRole('ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetplixApiResponse<Boolean> unlikeMovie(@PathVariable String movieId) {
        return NetplixApiResponse.ok(likeMovieUseCase.unlike(jwtTokenProvider.getUserId(), movieId));
    }
}
