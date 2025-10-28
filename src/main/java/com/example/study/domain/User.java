package com.example.study.domain;

import com.example.study.domain.enums.EmailStatus;
import com.example.study.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    /* 유저 권한 */
    @Enumerated(EnumType.STRING) // 문자열 저장
    private Role role = Role.USER; // 기본 설정

    private String email;

    /* 이메일 인증 여부 */
    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus = EmailStatus.UNVERIFIED;

    public void changeRole(Role role) throws IllegalAccessException {
        if (this.role == role) {
            throw new IllegalAccessException("이미 해당 권한을 소유 중 입니다.");
        }
        this.role = role;
    }

    public void verifyEmail() throws IllegalAccessException {
        if (this.emailStatus == EmailStatus.VERIFIED) {
            throw new IllegalAccessException("이미 인증된 계정입니다.");
        }
        this.emailStatus = EmailStatus.VERIFIED;
    }



}
