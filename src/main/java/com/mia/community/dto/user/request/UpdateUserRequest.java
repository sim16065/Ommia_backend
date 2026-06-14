package com.mia.community.dto.user.request;

import com.mia.community.common.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @NotBlank(message = ValidationMessage.NICKNAME_REQUIRED)
    @Size(max = 10, message = ValidationMessage.NICKNAME_SIZE)
    @Pattern(regexp = "^\\S+$", message = ValidationMessage.NICKNAME_INVALID)
    private String nickname;

    private String profileImage;

    public UpdateUserRequest() {}

    public String getNickname() {
        return nickname;
    }
    public String getProfileImage() {
        return profileImage;
    }
}


