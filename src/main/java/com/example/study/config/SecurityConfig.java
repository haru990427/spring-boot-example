package com.example.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_URI = {
            "/api/users/login",
            "/api/users/register",
            "/api/test/**"
//            "/api/admins/login",
//            "/api/admins/register",
//            "/api/organizations/login",
//            "/api/organizations/register",
//            "/api/users/create",
//            "/api/users/check/nickname/**",
//            "/api/users/test/**",
//            "/oauth/redirect/kakao",
//            "api/test/**"
    };

    private static final String[] TIMEPAY_URI = {
            "/api/timepay/**"
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URI).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .requestMatchers("/api/admins/**").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("USER", "ORGANIZATION")
                        .requestMatchers("/api/organizations/**").hasRole("ORGANIZATION")
                        .requestMatchers(TIMEPAY_URI).hasAnyRole("USER", "ADMIN", "ORGANIZATION")
                        .requestMatchers(HttpMethod.POST, "/api/notifications/**").authenticated()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
