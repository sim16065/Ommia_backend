// ChangePasswordRequest.java
package com.mia.community.dto.user.request;

import com.mia.community.common.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {

    @NotBlank(message = ValidationMessage.NEW_PASSWORD_REQUIRED)
    @Size(min = 8, max = 20, message = ValidationMessage.PASSWORD_SIZE)
    @Pattern(
            regexp = ValidationMessage.PASSWORD_REGEXP,
            message = ValidationMessage.PASSWORD_PATTERN
    )
    private String newPassword;

    @NotBlank(message = ValidationMessage.CONFIRM_PASSWORD_REQUIRED)
    private String confirmPassword;

    public ChangePasswordRequest() {}

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}