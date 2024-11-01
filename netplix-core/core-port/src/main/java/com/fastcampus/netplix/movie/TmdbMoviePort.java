package com.fastcampus.netplix.movie;

public interface TmdbMoviePort {

    TmdbPageableMovies fetchPageable(int page);
}
