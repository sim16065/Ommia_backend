package com.mia.community.dto.user.response;

import com.mia.community.entity.User;

public class AuthorResponse {
    private final Long id;
    private final String nickname;
    private final String profileImageUrl;

    public AuthorResponse(Long id, String nickname, String profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImage;
    }

    public static AuthorResponse from(User user) {
        return new AuthorResponse(user.getId(), user.getNickname(), user.getProfileImageUrl());
    }

    public Long getId() { return id; }
    public String getNickname() { return nickname; }
    public String getProfileImageUrl() { return profileImageUrl; }
}