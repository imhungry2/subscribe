package com.fastcampus.netplix.repository.movie;

import com.fastcampus.netplix.entity.movie.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MovieCustomRepository {

    Optional<MovieEntity> findByMovieName(String name);

    Page<MovieEntity> search(Pageable pageable);
}
