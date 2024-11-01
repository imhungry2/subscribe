package com.fastcampus.netplix.controller.user.request;

import com.fastcampus.netplix.annotation.PasswordEncryption;
import lombok.Getter;

@Getter
public class UserRegisterRequest {

    private final String username;

    @PasswordEncryption
    private String password;

    private final String email;

    private final String phone;

    public UserRegisterRequest(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}