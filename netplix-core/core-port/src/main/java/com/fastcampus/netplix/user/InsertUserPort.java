package com.fastcampus.netplix.user;

import com.fastcampus.netplix.user.response.UserPortResponse;

public interface InsertUserPort {

    UserPortResponse create(CreateUser user);

    UserPortResponse createSocialUser(String username, String provider, String providerId);
}
