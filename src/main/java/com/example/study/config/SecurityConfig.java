package com.example.study.config;

import com.example.study.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
            "/api/admins/login",
            "/api/admins/register",
            "/api/test/**",
            "/api/users/**", // role 설정 작업 끝나면 삭제
//            "/api/admins/**", // role 설정 작업 끝나면 삭제
//            "/api/users/test/**",
//            "/oauth/redirect/kakao",
    };

    private static final String[] USER_URI = {
            "/api/users/**"
    };

    private static final String[] ADMIN_URI = {
            "/api/admins/**"
    };

    private static final String[] SUPER_ADMIN_URI = {
            "/api/admins/register",
    };

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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
                        .requestMatchers(USER_URI).hasAnyRole("USER", "ADMIN",  "SUPER_ADMIN")
                        .requestMatchers(ADMIN_URI).hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers(SUPER_ADMIN_URI).hasAnyRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/notifications/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
