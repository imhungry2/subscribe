package com.fastcampus.netplix.user;

import com.fastcampus.netplix.exception.UserException;
import com.fastcampus.netplix.user.command.UserRegistrationCommand;
import com.fastcampus.netplix.user.response.UserPortResponse;
import com.fastcampus.netplix.user.response.UserRegistrationResponse;
import com.fastcampus.netplix.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements FetchUserUseCase, RegisterUserUseCase {

    private final FetchUserPort fetchUserPort;
    private final InsertUserPort insertUserPort;
    private final KakaoUserPort kakaoUserPort;

    @Override
    public UserResponse findUserByEmail(String email) {
        Optional<UserPortResponse> byEmail = fetchUserPort.findByEmail(email);

        if (byEmail.isEmpty()) {
            throw new UserException.UserDoesNotExistException();
        }

        UserPortResponse userPortResponse = byEmail.get();

        return UserResponse.builder()
                .userId(userPortResponse.getUserId())
                .username(userPortResponse.getUsername())
                .password(userPortResponse.getPassword())
                .email(userPortResponse.getEmail())
                .phone(userPortResponse.getPhone())
                .role(userPortResponse.getRole())
                .build();
    }

    @Override
    public UserResponse findByProviderId(String providerId) {
        return fetchUserPort.findByProviderId(providerId)
                .map(it -> UserResponse.builder()
                        .userId(it.getUserId())
                        .provider(it.getProvider())
                        .providerId(it.getProviderId())
                        .username(it.getUsername())
                        .role(it.getRole())
                        .build())
                .orElse(null);
    }

    @Override
    public UserResponse findKakaoUser(String accessToken) {
        UserPortResponse userFromKakao = kakaoUserPort.findUserFromKakao(accessToken);

        return UserResponse.builder()
                .provider(userFromKakao.getProvider())
                .providerId(userFromKakao.getProviderId())
                .username(userFromKakao.getUsername())
                .build();
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationCommand command) {
        Optional<UserPortResponse> byEmail = fetchUserPort.findByEmail(command.getEmail());

        if (byEmail.isPresent()) {
            throw new UserException.UserAlreadyExistException();
        }

        UserPortResponse response = insertUserPort.create(CreateUser.builder()
                .username(command.getUsername())
                .encryptedPassword(command.getEncryptedPassword())
                .email(command.getEmail())
                .phone(command.getPhone())
                .build()
        );

        return new UserRegistrationResponse(response.getUsername(), response.getPassword(), response.getEmail());
    }

    @Override
    public UserRegistrationResponse registerSocialUser(String username, String provider, String providerId) {
        Optional<UserPortResponse> byProviderId = fetchUserPort.findByProviderId(providerId);

        if (byProviderId.isPresent()) {
            return null;
        }

        UserPortResponse socialUser = insertUserPort.createSocialUser(username, provider, providerId);

        return new UserRegistrationResponse(socialUser.getUsername(), null, null);
    }
}
