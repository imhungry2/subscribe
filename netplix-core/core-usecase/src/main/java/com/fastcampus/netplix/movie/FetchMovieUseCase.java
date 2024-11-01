package com.fastcampus.netplix.movie;

import com.fastcampus.netplix.movie.response.PageableMoviesResponse;

public interface FetchMovieUseCase {

    PageableMoviesResponse fetchFromClien(int page);

    PageableMoviesResponse fetchFromDb(int page);
}
