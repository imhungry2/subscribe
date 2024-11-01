package com.fastcampus.netplix.repository.token;

import com.fastcampus.netplix.entity.token.TokenEntity;
import com.fastcampus.netplix.token.InsertTokenPort;
import com.fastcampus.netplix.token.FetchTokenPort;
import com.fastcampus.netplix.token.response.TokenPortResponse;
import com.fastcampus.netplix.token.UpdateTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class TokenRepository implements FetchTokenPort, InsertTokenPort, UpdateTokenPort {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public TokenPortResponse create(String userId, String accessToken, String refreshToken) {
        TokenEntity entity = TokenEntity.newTokenEntity(userId, accessToken, refreshToken);
        tokenJpaRepository.save(entity);

        return new TokenPortResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenPortResponse findByUserId(String userId) {
        return tokenJpaRepository.findByUserId(userId)
                .map(result -> new TokenPortResponse(result.getAccessToken(), result.getRefreshToken()))
                .orElse(null);
    }

    @Override
    public void updateToken(String userId, String accessToken, String refreshToken) {
        Optional<TokenEntity> byUserId = tokenJpaRepository.findByUserId(userId);

        if (byUserId.isEmpty()) {
            throw new RuntimeException();
        }

        TokenEntity tokenEntity = byUserId.get();
        tokenEntity.updateToken(accessToken, refreshToken);

        tokenJpaRepository.save(tokenEntity);
    }
}
