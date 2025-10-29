package com.example.study.controller.user.response.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {
    private UserInfoResponse data;
    private String accessToken;
    private String refreshToken;
}
