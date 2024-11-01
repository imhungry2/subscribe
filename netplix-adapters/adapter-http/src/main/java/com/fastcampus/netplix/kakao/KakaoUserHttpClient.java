package com.fastcampus.netplix.kakao;

import com.fastcampus.netplix.user.KakaoUserPort;
import com.fastcampus.netplix.user.response.UserPortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoUserHttpClient implements KakaoUserPort {

    private final String KAKAO_USERINFO_API_URL = "https://kapi.kakao.com/v2/user/me";

    @Override
    public UserPortResponse findUserFromKakao(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);  // 액세스 토큰을 Authorization 헤더에 추가

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                KAKAO_USERINFO_API_URL,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Long providerId = (Long) response.getBody().get("id");

        Map properties = (Map) response.getBody().get("properties");
        String nickname = (String) properties.get("nickname");

        return UserPortResponse.builder()
                .username(nickname)
                .providerId(providerId.toString())
                .provider("kakao")
                .build();
    }
}
