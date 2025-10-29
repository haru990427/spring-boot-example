package com.example.study.controller.user;


import com.example.study.controller.user.request.auth.UserLoginRequest;
import com.example.study.controller.user.response.ApiResponse;
import com.example.study.controller.user.response.auth.UserInfoResponse;
import com.example.study.controller.user.response.auth.UserLoginResponse;
import com.example.study.service.user.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        UserLoginResponse userLoginResponse = loginService.loginUser(request.toServiceDto());
        /* todo 아래 내용 찾아보자 */
        /* 로그인 실패 401!! 400이 필요할까? 잘못된 값 입력 오류? */
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(userLoginResponse)
                        .build()
        );
    }


}
