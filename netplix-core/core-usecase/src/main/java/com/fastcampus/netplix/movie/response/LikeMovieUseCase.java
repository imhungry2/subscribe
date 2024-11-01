package com.fastcampus.netplix.movie.response;

public interface LikeMovieUseCase {

    Boolean like(String userId, String movieId);

    Boolean unlike(String userId, String movieId);
}
