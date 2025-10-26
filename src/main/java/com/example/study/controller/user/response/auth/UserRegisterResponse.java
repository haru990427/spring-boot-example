package com.example.study.controller.user.response.auth;

import lombok.*;


@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {
    private boolean success;
    private UserInfoResponse userInfo; // 추후 JWT 토큰으로 변경 예정
    private String message;

}
