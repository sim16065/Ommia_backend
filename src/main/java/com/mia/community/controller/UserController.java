package com.mia.community.controller;

import com.mia.community.common.response.ApiResponse;
import com.mia.community.dto.user.request.ChangePasswordRequest;
import com.mia.community.dto.user.request.SignupRequest;
import com.mia.community.dto.user.request.UpdateUserRequest;
import com.mia.community.dto.user.response.SignupResponse;
import com.mia.community.dto.user.response.UserResponse;
import com.mia.community.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<ApiResponse<SignupResponse>> signup(
            @Valid @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);

            return ResponseEntity
                    .status(201)
                    .body(ApiResponse.success("회원가입이 완료되었습니다.", response));
    }

    // 내 회원 정보 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(
            @AuthenticationPrincipal Long userId) {
        UserResponse response = userService.getProfile(userId);

        return ResponseEntity.ok(ApiResponse.success("회원 정보를 성공적으로 불러왔습니다.", response));
    }

    // 내 회원 정보 수정
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.update(userId, request);

        return ResponseEntity.ok(ApiResponse.success("회원 정보가 수정되었습니다.", response));
    }

    // 비밀번호 변경
    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.updatePassword(userId, request);

        return ResponseEntity.ok(ApiResponse.success("비밀번호가 변경되었습니다.", null));
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal Long userId) {
        userService.delete(userId);

        return ResponseEntity.ok(ApiResponse.success("회원 탈퇴가 완료되었습니다.", null));
    }
}