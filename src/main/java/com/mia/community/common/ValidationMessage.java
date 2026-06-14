package com.mia.community.common;

public final class ValidationMessage {

    private ValidationMessage() {
    }

    public static final String EMAIL_REQUIRED = "이메일을 입력해주세요.";
    public static final String EMAIL_INVALID = "이메일 형식이 올바르지 않습니다.";

    public static final String PASSWORD_REQUIRED = "비밀번호를 입력해주세요.";
    public static final String PASSWORD_SIZE = "비밀번호는 8자 이상, 20자 이하로 입력해주세요.";
    public static final String PASSWORD_PATTERN = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다";
    public static final String PASSWORD_REGEXP = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).+$";
    public static final String NICKNAME_REQUIRED = "닉네임을 입력해주세요.";
    public static final String NICKNAME_INVALID = "닉네임에는 공백을 사용할 수 없습니다.";
    public static final String NICKNAME_SIZE = "닉네임은 최대 10자까지 가능합니다.";

    public static final String NEW_PASSWORD_REQUIRED = "새 비밀번호를 입력해주세요.";
    public static final String CONFIRM_PASSWORD_REQUIRED = "비밀번호 확인을 입력해주세요.";

    public static final String TITLE_REQUIRED = "제목을 입력해주세요.";
    public static final String TITLE_SIZE = "제목은 최대 26자까지 입력 가능합니다.";

    public static final String CONTENT_REQUIRED = "내용을 입력해주세요.";
}