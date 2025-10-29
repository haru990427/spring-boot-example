package com.example.study.service.user;

import com.example.study.controller.user.response.auth.UserInfoResponse;
import com.example.study.controller.user.response.auth.UserLoginResponse;
import com.example.study.domain.User;
import com.example.study.domain.UserRepository;
import com.example.study.domain.enums.Role;
import com.example.study.service.user.dto.UserLoginDto;
import com.example.study.utils.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public UserLoginResponse loginUser(UserLoginDto userData) {
        User user = userRepository.findByUsername(userData.getUsername())
                .filter(u -> passwordEncoder.matches(userData.getPassword(), u.getPassword()))
                .orElseThrow(() -> new BadCredentialsException("로그인 정보가 올바르지 않습니다."));

        String role = user.getRole().name();

        String accessToken = jwtUtils.generateAccessToken(
                user.getId(),
                user.getUsername(),
                List.of(role)
        );

        String refreshToken = jwtUtils.generateRefreshToken(
                user.getId(),
                user.getUsername()
        );

         /* 추후 최근 로그인 시간 추가해야징 */

        UserInfoResponse userInfoResponse =
                UserInfoResponse.builder()
                        .userID(user.getId())
                        .userName(user.getUsername())
                        .nickName(user.getNickname())
                        .role(user.getRole())
                        .email(user.getEmail())
                        .emailStatus(user.getEmailStatus())
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .build();

        return UserLoginResponse.builder()
                .data(userInfoResponse)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
