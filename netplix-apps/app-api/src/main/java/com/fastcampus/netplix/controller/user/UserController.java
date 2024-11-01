package com.fastcampus.netplix.controller.user;

import com.fastcampus.netplix.controller.NetplixApiResponse;
import com.fastcampus.netplix.controller.user.request.UserLoginRequest;
import com.fastcampus.netplix.controller.user.request.UserRegisterRequest;
import com.fastcampus.netplix.security.NetplixAuthUser;
import com.fastcampus.netplix.token.FetchTokenUseCase;
import com.fastcampus.netplix.token.UpdateTokenUseCase;
import com.fastcampus.netplix.user.FetchUserUseCase;
import com.fastcampus.netplix.user.RegisterUserUseCase;
import com.fastcampus.netplix.user.command.UserRegistrationCommand;
import com.fastcampus.netplix.user.response.UserRegistrationResponse;
import com.fastcampus.netplix.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final FetchTokenUseCase fetchTokenUseCase;
    private final FetchUserUseCase fetchUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateTokenUseCase updateTokenUseCase;

    private final AuthenticationManagerBuilder builder;

    @PostMapping("/api/v1/user/register")
    public NetplixApiResponse<UserRegistrationResponse> register(@RequestBody UserRegisterRequest request) {
        UserRegistrationResponse register = registerUserUseCase.register(
                UserRegistrationCommand.builder()
                        .email(request.getEmail())
                        .username(request.getUsername())
                        .encryptedPassword(request.getPassword())
                        .phone(request.getPhone())
                        .build()
        );

        return NetplixApiResponse.ok(register);
    }

    @PostMapping("/api/v1/user/login")
    public NetplixApiResponse<String> login(@RequestBody UserLoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticate = builder.getObject().authenticate(token);

        NetplixAuthUser principal = (NetplixAuthUser) authenticate.getPrincipal();

        return NetplixApiResponse.ok("access-token");
    }

    @PostMapping("/api/v1/user/callback")
    public NetplixApiResponse<String> kakaoCallback(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        String accessTokenFromKakao = fetchTokenUseCase.getTokenFromKakao(code);
        UserResponse kakaoUser = fetchUserUseCase.findKakaoUser(accessTokenFromKakao);

        UserResponse byProviderId = fetchUserUseCase.findByProviderId(kakaoUser.getProviderId());

        if (ObjectUtils.isEmpty(byProviderId)) {
            registerUserUseCase.registerSocialUser(
                    kakaoUser.getUsername(),
                    kakaoUser.getProvider(),
                    kakaoUser.getProviderId()
            );
        }

        return NetplixApiResponse.ok(updateTokenUseCase.upsertToken(kakaoUser.getProviderId()));
    }
}
