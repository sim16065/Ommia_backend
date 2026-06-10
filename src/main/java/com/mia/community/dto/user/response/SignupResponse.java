package com.community.user.dto.response;

import com.community.user.domain.User;

public class SignupResponse {
    private final Long userId;

    public SignupResponse(Long userId) {
        this.userId = userId;
    }

    public static SignupResponse from(User user) {
        return new SignupResponse(user.getId());
    }

    public Long getUserId() {
        return userId;
    }
}