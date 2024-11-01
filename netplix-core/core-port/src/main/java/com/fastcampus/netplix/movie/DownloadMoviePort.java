package com.fastcampus.netplix.movie;

public interface DownloadMoviePort {

    void save(UserMovieDownload domain);

    long downloadCountToday(String userId);
}
