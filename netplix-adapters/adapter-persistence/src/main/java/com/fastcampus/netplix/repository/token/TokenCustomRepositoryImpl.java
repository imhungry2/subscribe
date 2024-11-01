package com.fastcampus.netplix.repository.token;

import com.fastcampus.netplix.entity.token.TokenEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.fastcampus.netplix.entity.token.QTokenEntity.tokenEntity;

@Repository
@RequiredArgsConstructor
public class TokenCustomRepositoryImpl implements TokenCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<TokenEntity> findByUserId(String userId) {
        return queryFactory.selectFrom(tokenEntity)
                .where(tokenEntity.userId.eq(userId))
                .fetch()
                .stream().findFirst();
    }
}
