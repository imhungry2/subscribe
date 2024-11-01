package com.fastcampus.netplix.movie;

public interface UserDownloadMovieRoleValidator {

    boolean validate(long count);

    boolean isTarget(String role);
}
