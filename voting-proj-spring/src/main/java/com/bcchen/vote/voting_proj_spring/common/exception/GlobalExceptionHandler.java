package com.bcchen.vote.voting_proj_spring.common.exception;

import com.bcchen.vote.voting_proj_spring.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 處理業務邏輯錯誤
    @ExceptionHandler(BusinessException.class)
    public ApiResponse handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getMessage());
    }

    // 處理所有其他未預期的錯誤
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        log.error("系統發生非預期錯誤", e);
        return ApiResponse.error("系統發生非預期錯誤");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public void handleNoResourceFoundException() {
        // 忽略，不回傳任何東西
    }
}