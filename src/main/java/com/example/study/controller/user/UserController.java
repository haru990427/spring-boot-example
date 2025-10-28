package com.example.study.controller.user;

import com.example.study.controller.user.request.auth.UserRegisterRequest;
import com.example.study.controller.user.response.ApiResponse;
import com.example.study.controller.user.response.auth.UserRegisterResponse;
import com.example.study.domain.UserRepository;
import com.example.study.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest request) {
        UserRegisterResponse userRegisterResponse = userService.registerUser(request.toServiceDto());

        /* todo 브랜치 생성 및 관리 */
        if (!userRegisterResponse.isSuccess()) {
            if (userRegisterResponse.getMessage().equals("username")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        ApiResponse.builder()
                                .status(HttpStatus.CONFLICT.value())
                                .message("이미 사용중인 아이디 입니다.")
                                .build());

            } else if (userRegisterResponse.getMessage().equals("nickname")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        ApiResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message("이미 사용중인 닉네임 입니다.")
                        .build());

            } else if(userRegisterResponse.getMessage().equals("email")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        ApiResponse.builder()
                                .status(HttpStatus.CONFLICT.value())
                                .message("이미 사용중인 이메일 입니다.")
                                .build());
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data(userRegisterResponse)
                        .build());
    }
}
