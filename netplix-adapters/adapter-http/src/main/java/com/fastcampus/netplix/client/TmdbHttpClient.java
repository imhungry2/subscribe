package com.fastcampus.netplix.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TmdbHttpClient {

    private final HttpClient httpClient;

    @Value("${tmdb.auth.access-token}")
    private String accessToken;

    public String request(String uri, HttpMethod httpMethod, MultiValueMap<String, String> headers, Map<String, Object> params) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add(HttpHeaders.ACCEPT, "application/json");
        queryParams.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        queryParams.addAll(headers);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(queryParams);

        return httpClient.request(uri, httpMethod, httpHeaders, params);
    }
}
