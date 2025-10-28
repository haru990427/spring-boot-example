package com.example.study.controller.user;

import com.example.study.controller.user.request.auth.UserRegisterRequest;
import com.example.study.controller.user.response.auth.UserRegisterResponse;
import com.example.study.domain.UserRepository;
import com.example.study.service.user.UserService;
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

        /* todo http response DTO 만들기  */
        /* todo 각 응답 코드 기준? 정하기 */
        /* todo 브랜치 생성 및 관리 */
        if (!userRegisterResponse.isSuccess()) {
            if (userRegisterResponse.getMessage().equals("username")) {
                return ResponseEntity.status(409).body("아이디 중복");

            } else if (userRegisterResponse.getMessage().equals("nickname")) {
                return ResponseEntity.status(409).body("닉네임 중복");

            } else if(userRegisterResponse.getMessage().equals("email")) {
                return ResponseEntity.status(409).body("이메일 중복");
            }
        }

        return ResponseEntity.ok(userRegisterResponse);
    }
}
