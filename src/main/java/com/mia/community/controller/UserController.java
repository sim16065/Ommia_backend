package com.community.user.controller;

import com.community.common.response.ApiResponse;
import com.community.user.dto.request.ChangePasswordRequest;
import com.community.user.dto.request.LoginRequest;
import com.community.user.dto.request.SignupRequest;
import com.community.user.dto.request.UpdateUserRequest;
import com.community.user.dto.response.LoginResponse;
import com.community.user.dto.response.SignupResponse;
import com.community.user.dto.response.UserResponse;
import com.community.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request) {
        try {
            SignupResponse response = userService.signup(request);

            return ResponseEntity
                    .status(201)
                    .body(new ApiResponse<>("회원가입이 완료되었습니다.", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(400)
                    .body(new ApiResponse<>("invalid_request", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);

            return ResponseEntity.ok(
                    new ApiResponse<>("로그인에 성공했습니다.", response)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse<>("login_failed", null));
        }
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(@PathVariable Long userId) {

        UserResponse response = userService.getProfile(userId);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보를 성공적으로 불러왔습니다.", response));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.update(userId, request);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보가 수정되었습니다.", response));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<UserResponse>> changePassword(
            @PathVariable Long userId,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return ResponseEntity.ok(new ApiResponse<>("비밀번호가 변경되었습니다.", null));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok(new ApiResponse<>("회원 탈퇴가 완료되었습니다.", null));
    }
}