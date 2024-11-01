package com.fastcampus.netplix.repository.movie;

import com.fastcampus.netplix.entity.movie.UserMovieDownloadEntity;
import com.fastcampus.netplix.movie.DownloadMoviePort;
import com.fastcampus.netplix.movie.UserMovieDownload;
import com.fastcampus.netplix.repository.subscription.UserSubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserMovieDownloadRepository implements DownloadMoviePort {

    private final UserMovieDownloadJpaRepository userMovieDownloadJpaRepository;

    @Override
    public void save(UserMovieDownload domain) {
        userMovieDownloadJpaRepository.save(UserMovieDownloadEntity.toEntity(domain));
    }

    @Override
    public long downloadCountToday(String userId) {
        return userMovieDownloadJpaRepository.countDownloadToday(userId);
    }
}
