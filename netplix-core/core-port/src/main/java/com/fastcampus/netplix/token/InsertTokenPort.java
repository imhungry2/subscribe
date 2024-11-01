package com.fastcampus.netplix.token;

import com.fastcampus.netplix.token.response.TokenPortResponse;

public interface InsertTokenPort {

    TokenPortResponse create(String userId, String accessToken, String refreshToken);
}
