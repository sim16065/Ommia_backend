package com.mia.community.common.exception;

public enum ErrorCode {

    // Common
    UNAUTHORIZED(401, "로그인이 필요한 서비스입니다."),
    INVALID_IMAGE_FORMAT(400, "올바른 이미지 형식이 아닙니다."),
    INVALID_FILE(400, "올바르지 않은 파일입니다."),
    FILE_UPLOAD_FAILED(500, "파일 업로드에 실패하였습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 오류가 발생했습니다."),

    // Auth
    INVALID_LOGIN_CREDENTIALS(401, "이메일 또는 비밀번호를 확인해주세요."),
    INVALID_REFRESH_TOKEN(401, "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(401, "리프레시 토큰이 만료되었습니다."),

    // User - 회원가입
    PASSWORD_CONFIRMATION_MISMATCH(400, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    EMAIL_ALREADY_EXISTS(409, "이미 가입된 이메일입니다."),
    NICKNAME_ALREADY_EXISTS(409, "이미 사용 중인 닉네임입니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

    // 게시물
    POST_NOT_FOUND(404, "존재하지 않거나 삭제된 게시물입니다."),
    POST_UPDATE_DENIED(403, "게시물 수정 권한이 없습니다."),
    POST_DELETE_DENIED(403, "게시물 삭제 권한이 없습니다."),
    CANNOT_LIKE_OWN_POST(403, "본인이 작성한 글에는 좋아요를 누를 수 없습니다."),

    // 게시물 좋아요
    ALREADY_LIKED_POST(409, "이미 좋아요를 누른 게시물입니다."),

    // 댓글
    COMMENT_NOT_FOUND(404, "존재하지 않거나 삭제된 댓글입니다."),
    COMMENT_UPDATE_DENIED(403, "댓글 수정 권한이 없습니다."),
    COMMENT_DELETE_DENIED(403, "댓글 삭제 권한이 없습니다.");


    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() { return status; }
    public String getCode() { return this.name(); }
    public String getMessage() { return message; }
}