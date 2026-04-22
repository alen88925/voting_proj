package com.bcchen.vote.voting_proj_spring.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private final boolean success;
    private final String message;
    private final Object data;

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }
}
