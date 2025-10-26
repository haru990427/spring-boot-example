package com.example.study.controller.user;

import com.example.study.controller.user.request.auth.UserRegisterRequest;
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
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest request) {
        userService.registerUser(request.toServiceDto());
        return ResponseEntity.ok("OK?");
    }
}
