package com.example.study.controller.user.request.auth;

import com.example.study.service.user.dto.UserLoginDTO;
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

    public UserLoginDTO toServiceDto() {
        return new UserLoginDTO(
                this.username,
                this.password
        );
    }
}
