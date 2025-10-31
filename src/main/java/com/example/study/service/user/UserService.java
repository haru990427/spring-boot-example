package com.example.study.service.user;

import com.example.study.controller.user.response.auth.UserInfoResponse;
import com.example.study.controller.user.response.auth.UserRegisterResponse;
import com.example.study.domain.User;
import com.example.study.domain.UserRepository;
import com.example.study.domain.enums.EmailStatus;
import com.example.study.domain.enums.Role;
import com.example.study.service.user.dto.UserRegisterDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* return 중복 필드 */
    private String extractField(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage().toLowerCase();

        if (message.contains("username")) return "username";
        if (message.contains("nickname")) return "nickname";
        if (message.contains("email")) return "email";

        return "duplicate: " + message;
    }

    @Transactional
    public UserRegisterResponse createUser(UserRegisterDTO userData) {
        log.info("회원가입 시작 - 아이디: {}", userData.getUsername());

        if (this.userRepository.existsByUsername(userData.getUsername())) {
             /* 아이디 중복 */
            log.warn("아이디 중복 탐지");
            log.warn("이미 가입된 아이디: {}",  userData.getUsername());
            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message("username")
                    .build();
        }

        if (this.userRepository.existsByNickname(userData.getNickname())) {
            /* 닉네임 중복 */
            log.warn("닉네임 중복 탐지");
            log.warn("이미 가입된 닉네임: {}", userData.getNickname());
            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message("nickname")
                    .build();
        }

        if (this.userRepository.existsByEmail(userData.getEmail())) {
            /* 이메일 중복 */
            log.warn("이메일 중복 탐지");
            log.warn("이미 가입된 이메일: {}",  userData.getEmail());
            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message("email")
                    .build();
        }

        String encodedPassword = passwordEncoder.encode(userData.getPassword());
        log.info("비밀번호 인코딩 완료");

        User newUser = User.builder()
                .username(userData.getUsername())
                .password(encodedPassword)
                .nickname(userData.getNickname())
                .role(Role.USER)
                .email(userData.getEmail())
                .emailStatus(EmailStatus.UNVERIFIED)
                .build();
        log.info("사용자({}) 객체 생성 완료", userData.getUsername());

        try {
            User savedUser = this.userRepository.save(newUser);
            log.info("사용자({}) DB 저장 완료",  userData.getUsername());

            UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                    .userID(savedUser.getId())
                    .userName(savedUser.getUsername())
                    .nickName(savedUser.getNickname())
                    .role(savedUser.getRole())
                    .email(savedUser.getEmail())
                    .emailStatus(savedUser.getEmailStatus())
                    .createdAt(savedUser.getCreatedAt())
                    .updatedAt(savedUser.getUpdatedAt())
                    .build();

            return UserRegisterResponse.builder()
                    .success(true)
                    .userInfo(userInfoResponse)
                    .message(null)
                    .build();
        } catch (DataIntegrityViolationException e) { // 코드로 중복 검사 해도 예외 처리 (race condition)
            log.warn("경합 조건 발생 -> 이미 가입된 계정 필드 감지: {}", e.getMessage());
            String field = extractField(e);
            log.warn("중복된 필드: {}", field);

            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message(field)
                    .build();
        } catch (Exception e) {
            log.error("회원가입 중 예기치 못한 오류 발생 - 오류가 발생한 아이디: {}", userData.getUsername(), e);

            return UserRegisterResponse.builder()
                    .success(false)
                    .userInfo(null)
                    .message("something went wrong")
                    .build();
        }
    }

//    public UserInfoResponse getMyInfo(Long userId) {
//        User user =  this.userRepository.findById(userId).orElse(null);
//        return UserInfoResponse.builder()
//                .userID(user.getId())
//                .userName(user.getUsername())
//                .nickName(user.getNickname())
//                .role(user.getRole())
//                .email(user.getEmail())
//                .emailStatus(user.getEmailStatus())
//                .createdAt(user.getCreatedAt())
//                .updatedAt(user.getUpdatedAt())
//                .build();
//    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        User user =  this.userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("존재하지 않는 유저입니다."));
        return UserInfoResponse.builder()
                .userID(user.getId())
                .userName(user.getUsername())
                .nickName(user.getNickname())
                .role(user.getRole())
                .email(user.getEmail())
                .emailStatus(user.getEmailStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
