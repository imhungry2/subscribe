package com.fastcampus.netplix.user;

import com.fastcampus.netplix.user.response.UserResponse;

public interface FetchUserUseCase {

    UserResponse findUserByEmail(String email);

    UserResponse findByProviderId(String providerId);

    UserResponse findKakaoUser(String accessToken);
}
