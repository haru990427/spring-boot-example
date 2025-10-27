package com.example.study.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailStatus {
    UNVERIFIED("미인증"),
    VERIFIED("인증됨");
    // EXPIRED("만료됨"); // 추후 사용할지도?

    /* 유지 보수? */
    private final String description;
}
