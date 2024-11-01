package com.fastcampus.netplix.token;

public interface UpdateTokenPort {

    void updateToken(String userId, String accessToken, String refreshToken);
}
