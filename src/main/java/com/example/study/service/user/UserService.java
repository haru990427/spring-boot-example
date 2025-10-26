package com.example.study.service.user;

import com.example.study.domain.User;
import com.example.study.domain.UserRepository;
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



    public void registerUser(UserRegisterDTO dto) {
        if (this.userRepository.findByUsername(dto.getUsername()).isPresent()) {
             /* 아이디 중복 */

        }

        if (this.userRepository.findByNickname(dto.getNickname()).isPresent()) {
            /* 닉네임 중복 */

        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User newUser = User.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .nickname(dto.getNickname())
                .build();
        try {
            this.userRepository.save(newUser);
        } catch (Exception e) {
            /* 오류 발생 */
        }
    }

}
