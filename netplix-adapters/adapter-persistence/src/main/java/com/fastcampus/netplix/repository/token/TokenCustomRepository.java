package com.fastcampus.netplix.repository.token;

import com.fastcampus.netplix.entity.token.TokenEntity;

import java.util.Optional;

public interface TokenCustomRepository {

    Optional<TokenEntity> findByUserId(String userId);
}
