package com.example.study.controller.user.response.auth;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long userID; // 식별자
    private String userName; // 아이디
    private String nickName; // 닉네임
    private String role;
    private String email;
    private String emailStatus;
    private LocalDateTime createdAt;
}
