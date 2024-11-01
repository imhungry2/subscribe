package com.fastcampus.netplix.repository.movie;

import com.fastcampus.netplix.entity.movie.UserMovieLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMovieLikeJpaRepository extends JpaRepository<UserMovieLikeEntity, String> {

    Optional<UserMovieLikeEntity> findByUserIdAndMovieId(final String userId, final String movieId);
}
