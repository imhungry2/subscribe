package com.fastcampus.netplix.token;

import com.fastcampus.netplix.user.response.UserResponse;

public interface FetchTokenUseCase {

    UserResponse findUserByAccessToken(String accessToken);

    boolean validateToken(String accessToken);

    String getTokenFromKakao(String code);
}
