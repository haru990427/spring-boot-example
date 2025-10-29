package com.example.study.controller.user.request.auth;

import com.example.study.service.user.dto.UserLoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    private String username;
    private String password;

    public UserLoginDto toServiceDto() {
        return new UserLoginDto(
                this.username,
                this.password
        );
    }
}
