package com.example.study.service.user;

import com.example.study.controller.user.response.auth.UserInfoResponse;
import com.example.study.controller.user.response.auth.UserRegisterResponse;
import com.example.study.domain.User;
import com.example.study.domain.UserRepository;
import com.example.study.domain.enums.EmailStatus;
import com.example.study.domain.enums.Role;
import com.example.study.service.user.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public UserRegisterResponse createUser(UserRegisterDTO userData) {
        if (this.userRepository.findByUsername(userData.getUsername()).isPresent()) {
             /* 아이디 중복 */
            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message("username")
                    .build();

        }

        if (this.userRepository.findByNickname(userData.getNickname()).isPresent()) {
            /* 닉네임 중복 */
            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message("nickname")
                    .build();

        }

        if (this.userRepository.findByEmail(userData.getEmail()).isPresent()) {
            /* 이메일 중복 */
            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message("email")
                    .build();
        }

        String encodedPassword = passwordEncoder.encode(userData.getPassword());
        User newUser = User.builder()
                .username(userData.getUsername())
                .password(encodedPassword)
                .nickname(userData.getNickname())
                .role(Role.USER)
                .email(userData.getEmail())
                .emailStatus(EmailStatus.UNVERIFIED)
                .build();
        try {
            this.userRepository.save(newUser);
        } catch (Exception e) {
            /* 오류 발생 */
        }

        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .userID(newUser.getId())
                .userName(newUser.getUsername())
                .nickName(newUser.getNickname())
                .role(newUser.getRole())
                .email(newUser.getEmail())
                .emailStatus(newUser.getEmailStatus())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();

        return UserRegisterResponse.builder()
                .success(true)
                .userInfo(userInfoResponse)
                .message(null)
                .build();
    }

}
