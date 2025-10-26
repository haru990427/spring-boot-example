package com.example.study.controller.user.response.auth;


import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long userID; // 식별자
    private String userName; // 아이디
    private String nickName; // 닉네임
}
