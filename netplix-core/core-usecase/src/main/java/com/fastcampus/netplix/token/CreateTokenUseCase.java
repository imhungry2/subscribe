package com.fastcampus.netplix.token;

import com.fastcampus.netplix.token.response.TokenResponse;

public interface CreateTokenUseCase {

    TokenResponse createNewToken(String userId);
}
