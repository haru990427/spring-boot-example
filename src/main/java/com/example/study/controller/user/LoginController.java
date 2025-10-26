package com.example.study.controller.user;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("pong");
    }


}
