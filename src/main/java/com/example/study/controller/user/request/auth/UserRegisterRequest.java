package com.example.study.controller.user.request.auth;

import com.example.study.service.user.dto.UserRegisterDTO;
import org.modelmapper.ModelMapper;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    private String username;

    private String password;

    private String nickname;

    private String email;


    public UserRegisterDTO toServiceDto() {
        return new UserRegisterDTO(
                this.username,
                this.password,
                this.nickname,
                this.email
        );
    }

    /* 수동 매핑에 비해 느리고, 자동 매핑이라 어디서 잘못된 건지 찾기 어렵고, 복잡한 구조는 수동 매핑이 더 나음  */
//    public UserRegisterDTO toServiceDto() {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(this, UserRegisterDTO.class);
//    }

}
