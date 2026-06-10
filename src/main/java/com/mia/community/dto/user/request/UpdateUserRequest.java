package com.community.user.dto.request;

public class UpdateUserRequest {
    private String nickname;
    private String profileImage;

    public UpdateUserRequest() {}

    public UpdateUserRequest(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}


