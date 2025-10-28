package com.example.study.controller.user;

import com.example.study.controller.user.request.auth.UserRegisterRequest;
import com.example.study.controller.user.response.ApiResponse;
import com.example.study.controller.user.response.auth.UserRegisterResponse;
import com.example.study.domain.UserRepository;
import com.example.study.service.user.UserService;
import io.swagger.annotations.Api;
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        /* todo get 기능 개발 */
        /* 200, 404 */

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(userId) // 여기에 유저 넣기
                        .build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> changeUserDetails(@PathVariable Long userId) {
        /* todo put 기능 개발 */
        /* 상황에 맞게 200(변경값 있음), 204(put을 요청했지만 변경값 없음), 404(없는 userId)  */

        return ResponseEntity.ok("put");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        /* todo delete 기능 개발 */
        /* 204(삭제 성공), 404(없는 userId) */
        return ResponseEntity.noContent().build();
    }

}
