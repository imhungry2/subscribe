package com.fastcampus.netplix.entity.movie;

import com.fastcampus.netplix.audit.MutableBaseEntity;
import com.fastcampus.netplix.movie.UserMovieLike;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_movie_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserMovieLikeEntity extends MutableBaseEntity {

    @Id
    @Column(name = "USER_MOVIE_LIKE_ID")
    private String userMovieLikeId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MOVIE_ID")
    private String movieId;

    @Column(name = "LIKE_YN")
    private Boolean likeYn;

    public UserMovieLike toDomain() {
        return UserMovieLike.builder()
                .userMovieLikeId(this.userMovieLikeId)
                .userId(userId)
                .movieId(movieId)
                .likeYn(this.likeYn)
                .build();
    }

    public static UserMovieLikeEntity toEntity(UserMovieLike domain) {
        return new UserMovieLikeEntity(
                domain.getUserMovieLikeId(), domain.getUserId(), domain.getMovieId(), domain.getLikeYn()
        );
    }
}
