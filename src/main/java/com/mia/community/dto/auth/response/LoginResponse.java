package com.mia.community.dto.user.response;

public class LoginResponse {
    private final Long userId;
    private final String accessToken;

    public LoginResponse(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }
    public String getAccessToken() {
        return accessToken;
    }
}