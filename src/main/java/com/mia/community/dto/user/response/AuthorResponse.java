package com.mia.community.dto.user.response;

import com.mia.community.entity.User;

public class AuthorResponse {
    private final Long userId;
    private final String nickname;
    private final String profileImageUrl;

    public AuthorResponse(Long userId, String nickname, String profileImage) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImage;
    }

    public static AuthorResponse from(User user) {
        return new AuthorResponse(user.getId(), user.getNickname(), user.getProfileImageUrl());
    }

    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getProfileImageUrl() { return profileImageUrl; }
}