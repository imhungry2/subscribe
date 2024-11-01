package com.fastcampus.netplix.user;

import com.fastcampus.netplix.user.command.UserRegistrationCommand;
import com.fastcampus.netplix.user.response.UserRegistrationResponse;

public interface RegisterUserUseCase {

    UserRegistrationResponse register(UserRegistrationCommand command);

    UserRegistrationResponse registerSocialUser(String username, String provider, String providerId);
}
