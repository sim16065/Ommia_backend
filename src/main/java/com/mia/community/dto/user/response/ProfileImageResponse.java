package com.mia.community.dto.user.response;

public class ProfileImageResponse {
    private final String profileImageUrl;

    public  ProfileImageResponse(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
