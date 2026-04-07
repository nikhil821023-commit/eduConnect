package com.nikhil.educonnect.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String timestamp;
    private String errorCode;

    // Private constructor — always use static methods
    private ApiResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }

    // ✅ Use this when request succeeds
    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.message = message;
        response.data = data;
        response.errorCode = null;
        return response;
    }

    // ❌ Use this when something goes wrong
    public static <T> ApiResponse<T> error(String message, String errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.message = message;
        response.data = null;
        response.errorCode = errorCode;
        return response;
    }

    // Getters — Spring needs these to convert to JSON
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public String getTimestamp() { return timestamp; }
    public String getErrorCode() { return errorCode; }
}