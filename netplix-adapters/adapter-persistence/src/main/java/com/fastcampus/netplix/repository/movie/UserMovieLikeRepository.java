package com.fastcampus.netplix.repository.movie;

import com.fastcampus.netplix.entity.movie.UserMovieLikeEntity;
import com.fastcampus.netplix.movie.LikeMoviePort;
import com.fastcampus.netplix.movie.UserMovieLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserMovieLikeRepository implements LikeMoviePort {

    private final UserMovieLikeJpaRepository userMovieLikeJpaRepository;

    @Override
    public UserMovieLike save(UserMovieLike domain) {
        UserMovieLikeEntity entity = UserMovieLikeEntity.toEntity(domain);
        return userMovieLikeJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<UserMovieLike> findByUserIdAndMovieId(String userId, String movieId) {
        return userMovieLikeJpaRepository.findByUserIdAndMovieId(userId, movieId)
                .map(UserMovieLikeEntity::toDomain);
    }
}
