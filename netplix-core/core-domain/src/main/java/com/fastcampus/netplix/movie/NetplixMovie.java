package com.fastcampus.netplix.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NetplixMovie {

    private final String movieName;
    private final boolean isAdult;
    private final String genre;
    private final String overview;
    private final String releaseAt;

    public NetplixMovie(String movieName, boolean isAdult, String genre, String overview, String releaseAt) {
        this.movieName = movieName;
        this.isAdult = isAdult;
        this.genre = genre;
        this.overview = overview;
        this.releaseAt = releaseAt;
    }
}
