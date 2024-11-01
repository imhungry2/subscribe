package com.fastcampus.netplix.repository.movie;

import com.fastcampus.netplix.entity.movie.MovieEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.fastcampus.netplix.entity.movie.QMovieEntity.movieEntity;

@Repository
@RequiredArgsConstructor
public class MovieCustomRepositoryImpl implements MovieCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MovieEntity> findByMovieName(String name) {
        return queryFactory.selectFrom(movieEntity)
                .where(movieEntity.movieName.eq(name))
                .fetch()
                .stream()
                .findFirst();
    }

    @Override
    public Page<MovieEntity> search(Pageable pageable) {
        List<MovieEntity> fetch = queryFactory.selectFrom(movieEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = queryFactory.selectFrom(movieEntity)
                .fetch()
                .size();

        return new PageImpl<>(fetch, pageable, count);
    }
}
