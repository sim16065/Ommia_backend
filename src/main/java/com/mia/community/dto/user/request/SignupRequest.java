package com.mia.community.dto.user.request;

import com.mia.community.common.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupRequest {

    @NotBlank(message = ValidationMessage.EMAIL_REQUIRED)
    @Email(message = ValidationMessage.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ValidationMessage.PASSWORD_REQUIRED)
    @Size(min = 8, max = 20, message = ValidationMessage.PASSWORD_SIZE)
    @Pattern(
            regexp = ValidationMessage.PASSWORD_REGEXP,
            message = ValidationMessage.PASSWORD_PATTERN
    )
    private String password;

    @NotBlank(message = ValidationMessage.NICKNAME_REQUIRED)
    @Size(max = 10, message = ValidationMessage.NICKNAME_SIZE)
    @Pattern(regexp = "^\\S+$", message = ValidationMessage.NICKNAME_INVALID)
    private String nickname;

    private String profileImageUrl;

    public SignupRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}