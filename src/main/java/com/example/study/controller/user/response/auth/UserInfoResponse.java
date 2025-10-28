package com.example.study.controller.user.response.auth;


import com.example.study.domain.enums.EmailStatus;
import com.example.study.domain.enums.Role;
import lombok.*;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
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
    private Role role;
    private String email;
    private EmailStatus emailStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
