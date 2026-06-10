package com.community.user.service;

import com.community.user.domain.User;
import com.mia.community.dto.user.request.ChangePasswordRequest;
import com.mia.community.dto.user.request.LoginRequest;
import com.mia.community.dto.user.request.SignupRequest;
import com.mia.community.dto.user.request.UpdateUserRequest;
import com.mia.community.dto.user.response.LoginResponse;
import com.mia.community.dto.user.response.SignupResponse;
import com.mia.community.dto.user.response.UserResponse;
import com.community.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return UserResponse.from(user);
    }

    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getProfileImage()
        );

        User savedUser = userRepository.save(user);

        return SignupResponse.from(savedUser);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인에 실패했습니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("로그인에 실패했습니다.");
        }

        return new LoginResponse(user.getId(), "fake-access-token");
    }

    public UserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setNickname(request.getNickname());
        user.setProfileImage(request.getProfileImage());

        User updatedUser = userRepository.save(user);

        return UserResponse.from(updatedUser);
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

    public void delete(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        userRepository.deleteById(userId);
    }
}