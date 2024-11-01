package com.fastcampus.netplix.movie;

import com.fastcampus.netplix.movie.response.MovieResponse;

import java.util.List;

public interface InsertMovieUseCase {

    void insert(List<MovieResponse> items);
}
