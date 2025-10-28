package com.example.study.controller.user.response;

import lombok.*;

@Getter
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
}
