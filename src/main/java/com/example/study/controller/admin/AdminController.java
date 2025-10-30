package com.example.study.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @PostMapping("/test")
    public ResponseEntity<?> adminTest() {
        return ResponseEntity.ok("호출됨 / 권한 검사중");
    }
}
