package com.mia.community.controller;

import com.mia.community.common.response.ApiResponse;
import com.mia.community.dto.auth.request.LoginRequest;
import com.mia.community.dto.auth.response.LoginResponse;
import com.mia.community.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(request, response);

        return ResponseEntity.ok(ApiResponse.success("로그인에 성공했습니다.", loginResponse));
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refresh(HttpServletRequest request) {
        String accessToken = authService.refresh(request);
        return ResponseEntity.ok(ApiResponse.success("토큰이 성공적으로 재발급되었습니다.", accessToken));
    }

    // 로그아웃 추가 예정
}
