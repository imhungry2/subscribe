package com.fastcampus.netplix.user;

import com.fastcampus.netplix.user.response.UserPortResponse;

import java.util.Optional;

public interface FetchUserPort {

    Optional<UserPortResponse> findByEmail(String email);

    Optional<UserPortResponse> findByProviderId(String providerId);
}
