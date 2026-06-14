package com.mia.community.common.response;

public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    public ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 성공 data 있음
    public static <T> ApiResponse<T> success(String message, T data) {
        return  new ApiResponse<>("SUCCESS", message, data);
    }

    // 성공 data null
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(null, message, null);
    }

    // 실패
    public  static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
