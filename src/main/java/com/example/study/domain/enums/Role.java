package com.example.study.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("사용자"),
    ADMIN("관리자"),
    SUPER_ADMIN("최고 관리자"); // 관리자 지정 가능

    private final String description;

    public boolean canAccessUser() {
        return this == USER || this == ADMIN || this == SUPER_ADMIN;
    }

    public boolean canAccessAdmin() {
        return this ==  ADMIN || this == SUPER_ADMIN;
    }

    public boolean canAccessSuperAdmin() {
        return this == SUPER_ADMIN;
    }

}
