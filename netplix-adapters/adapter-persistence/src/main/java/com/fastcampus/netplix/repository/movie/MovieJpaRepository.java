package com.fastcampus.netplix.repository.movie;

import com.fastcampus.netplix.entity.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, String>, MovieCustomRepository {
}
