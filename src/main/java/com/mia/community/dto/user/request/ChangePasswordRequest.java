// ChangePasswordRequest.java
package com.community.user.dto.request;

public class ChangePasswordRequest {
    private String newPassword;
    private String confirmPassword;

    public ChangePasswordRequest() {}

    public ChangePasswordRequest(String newPassword, String confirmPassword) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}