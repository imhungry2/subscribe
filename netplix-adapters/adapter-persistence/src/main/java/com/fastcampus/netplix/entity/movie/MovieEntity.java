package com.fastcampus.netplix.entity.movie;

import com.fastcampus.netplix.audit.MutableBaseEntity;
import com.fastcampus.netplix.movie.NetplixMovie;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Entity
@Table(name = "movie")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MovieEntity extends MutableBaseEntity {

    @Id
    @Column(name = "MOVIE_ID")
    private String movieId;

    @Column(name = "MOVIE_NAME")
    private String movieName;

    @Column(name = "IS_ADULT")
    private boolean isAdult;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "OVERVIEW")
    private String overview;

    @Column(name = "RELEASED_AT")
    private String releasedAt;

    public NetplixMovie toDomain() {
        return NetplixMovie.builder()
                .movieName(this.movieName)
                .isAdult(this.isAdult)
                .genre(this.genre)
                .overview(this.overview)
                .releaseAt(this.releasedAt)
                .build();
    }

    public static MovieEntity newMovieEntity(String movieName, boolean isAdult, String genre, String overview, String releasedAt) {
        return new MovieEntity(
                UUID.randomUUID().toString(),
                movieName,
                isAdult,
                genre,
                getSubstrOverview(overview),
                releasedAt
        );
    }

    public static String getSubstrOverview(String overview) {
        if (StringUtils.isBlank(overview)) {
            return "별도의 설명이 존재하지 않습니다.";
        }

        return overview.substring(0, Math.min(overview.length(), 200));
    }
}
