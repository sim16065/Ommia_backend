package com.mia.community.common.exception;

import com.mia.community.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 예외 처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 로직 에러 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    // DTO 유효성 검증 및 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();
        return ResponseEntity
                .status(400)
                .body(ApiResponse.error("VALIDATION_ERROR", message));
    }

    // 서버 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {

        return ResponseEntity
                .status(500)
                .body(ApiResponse.error("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."));
    }
}