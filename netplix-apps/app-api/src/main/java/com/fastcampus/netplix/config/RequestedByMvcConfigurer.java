package com.fastcampus.netplix.config;

import com.fastcampus.netplix.interceptor.RequestedByInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class RequestedByMvcConfigurer implements WebMvcConfigurer {

    private final RequestedByInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(interceptor);
    }
}
