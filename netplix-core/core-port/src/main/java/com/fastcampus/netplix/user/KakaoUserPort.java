package com.fastcampus.netplix.user;

import com.fastcampus.netplix.user.response.UserPortResponse;

public interface KakaoUserPort {

    UserPortResponse findUserFromKakao(String accessToken);
}
