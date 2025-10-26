package com.example.study.config;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secretKey;

}
