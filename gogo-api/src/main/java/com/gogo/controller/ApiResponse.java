package com.gogo.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    public static final ApiResponse<String> OK = new ApiResponse<>("", "", "OK");

    private final String errorCode;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>("", "", data);
    }

}
