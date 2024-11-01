package com.fastcampus.netplix.token;

import com.fastcampus.netplix.token.response.TokenPortResponse;

public interface FetchTokenPort {

    TokenPortResponse findByUserId(String userId);
}
