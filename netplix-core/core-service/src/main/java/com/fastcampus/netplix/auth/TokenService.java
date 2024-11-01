package com.fastcampus.netplix.auth;

import com.fastcampus.netplix.token.*;
import com.fastcampus.netplix.token.response.TokenPortResponse;
import com.fastcampus.netplix.token.response.TokenResponse;
import com.fastcampus.netplix.user.FetchUserUseCase;
import com.fastcampus.netplix.user.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parser;

@Service
@RequiredArgsConstructor
public class TokenService implements FetchTokenUseCase, CreateTokenUseCase, UpdateTokenUseCase {

    @Value("${jwt.secret}")
    private String secretKey;

    private final InsertTokenPort insertTokenPort;
    private final UpdateTokenPort updateTokenPort;
    private final FetchTokenPort fetchTokenPort;
    private final KakaoTokenPort kakaoTokenPort;

    private final FetchUserUseCase fetchUserUseCase;

    @Override
    public UserResponse findUserByAccessToken(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Object userId = claims.get("userId");

        if (ObjectUtils.isEmpty(userId)) {
            throw new RuntimeException();
        }

        return fetchUserUseCase.findByProviderId(userId.toString());
    }

    @Override
    public boolean validateToken(String accessToken) {
        parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken);

        return true;
    }

    @Override
    public String getTokenFromKakao(String code) {
        return kakaoTokenPort.getAccessTokenByCode(code);
    }

    @Override
    public TokenResponse createNewToken(String userId) {
        String accessToken = getToken(userId, Duration.ofHours(3));
        String refreshToken = getToken(userId, Duration.ofHours(24));

        TokenPortResponse tokenPortResponse = insertTokenPort.create(userId, accessToken, refreshToken);

        return TokenResponse.builder()
                .refreshToken(tokenPortResponse.getRefreshToken())
                .accessToken(tokenPortResponse.getAccessToken())
                .build();
    }

    @Override
    public String upsertToken(String providerId) {
        TokenPortResponse byUserId = fetchTokenPort.findByUserId(providerId);
        String accessToken = getToken(providerId, Duration.ofHours(3));
        String refreshToken = getToken(providerId, Duration.ofHours(24));

        if (byUserId == null) {
            insertTokenPort.create(providerId, accessToken, refreshToken);
        } else {
            updateTokenPort.updateToken(providerId, accessToken, refreshToken);
        }

        return accessToken; // access, refresh 를 함께 반환하여야 한다면 별도의 객체를 생성하여 리턴
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getToken(String userId, Duration expireAt) {
        Date now = new Date();
        Instant instant = now.toInstant();

        return builder()
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(Date.from(instant.plus(expireAt)))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
