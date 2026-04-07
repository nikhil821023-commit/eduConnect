package com.nikhil.educonnect.exceptions;

import com.nikhil.educonnect.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles: teacher/school not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity
                .status(404)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "NOT_FOUND"
                ));
    }

    // Handles: duplicate email registration
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleUserExists(
            UserAlreadyExistsException ex) {

        return ResponseEntity
                .status(409)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        "USER_ALREADY_EXISTS"
                ));
    }

    // Handles: any other unexpected error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneral(
            Exception ex) {

        return ResponseEntity
                .status(500)
                .body(ApiResponse.error(
                        "Something went wrong. Please try again.",
                        "INTERNAL_ERROR"
                ));
    }
}