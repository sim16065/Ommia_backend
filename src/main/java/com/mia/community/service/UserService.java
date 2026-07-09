package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.entity.User;
import com.mia.community.dto.user.request.ChangePasswordRequest;
import com.mia.community.dto.user.request.SignupRequest;
import com.mia.community.dto.user.request.UpdateUserRequest;
import com.mia.community.dto.user.response.SignupResponse;
import com.mia.community.dto.user.response.UserResponse;
import com.mia.community.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadService fileUploadService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileUploadService = fileUploadService;
    }

    // 사용자 프로필 조회
    public UserResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.from(user);
    }

     // 회원가입
    // 이메일과 닉네임 중복 확인 후, 비밀번호 암호화하여 저장
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getProfileImageUrl()
        );

        return SignupResponse.from(userRepository.save(user));
    }

    // 사용자 프로필 수정
    public UserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getNickname().equals(request.getNickname())
                && userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        // 기존 이미지와 새 이미지가 다르면 기존 이미지 삭제 (기존 이미지가 null 이 아닌 경우)
        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().equals(request.getProfileImageUrl())) {
            fileUploadService.deleteFile(user.getProfileImageUrl());
        }

        user.updateProfile(request.getNickname(), request.getProfileImageUrl());

        return UserResponse.from(userRepository.save(user));
    }

    // 비밀번호 변경
    public void updatePassword(Long userId, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_CONFIRMATION_MISMATCH);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // 사용자 삭제
    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}