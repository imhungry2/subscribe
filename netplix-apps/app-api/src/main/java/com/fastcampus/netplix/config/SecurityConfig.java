package com.fastcampus.netplix.config;

import com.fastcampus.netplix.filter.JwtAuthenticationFilter;
import com.fastcampus.netplix.filter.UserHistoryLoggingFilter;
import com.fastcampus.netplix.security.NetplixUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final NetplixUserDetailsService netplixUserDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserHistoryLoggingFilter userHistoryLoggingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.userDetailsService(netplixUserDetailsService);

        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                                "/api/v1/user/register",
                                "/api/v1/user/login",
                                "/api/v1/user/callback"
                        ).permitAll()
                        .anyRequest().authenticated()
        );

        http.oauth2Login(oauth2 -> oauth2.failureUrl("/login?error=true"));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(userHistoryLoggingFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
            configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
            configuration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
            configuration.setAllowCredentials(true);

            return configuration;
        };
    }
}
