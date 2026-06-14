package com.mia.community.service;

import com.mia.community.common.JwtAuthenticationUtil;
import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.entity.User;
import com.mia.community.dto.auth.request.LoginRequest;
import com.mia.community.dto.auth.response.LoginResponse;
import com.mia.community.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtAuthenticationUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 로그인: 이메일로 사용자 조회 및 비밀번호 일치 확인한 뒤 토큰 발급
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_LOGIN_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_LOGIN_CREDENTIALS);
        }

        String accessToken = jwtUtil.generateToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        // Refresh Token은 HttpOnly 쿠키에 저장
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 쿠키 저장 기간 설정
        response.addCookie(cookie);

        return new LoginResponse(user.getId(), accessToken);
    }

    // Refresh Token 검증 . Access Token 재발급
    public String refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("refreshToken"))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        return jwtUtil.generateToken(userId);
    }
}