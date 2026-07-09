package com.mia.community.dto.user.response;

import com.mia.community.entity.User;

public class UserResponse {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public UserResponse() {

    }

    public UserResponse(Long userId, String email, String nickname, String profileImageUrl) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    // Getter
    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    // from() 메서드
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getNickname(), user.getProfileImageUrl());
    }
}
