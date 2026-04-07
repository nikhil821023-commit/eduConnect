package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> home() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "EduConnect+",
                        "Server is running 🚀"
                )
        );
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {

        Map<String, String> info = new HashMap<>();
        info.put("status", "UP");
        info.put("app", "EduConnect+");
        info.put("version", "1.0");
        info.put("database", "PostgreSQL");

        return ResponseEntity.ok(
                ApiResponse.success(info, "Application is healthy ✅")
        );
    }

    // Test error handling is working
    @GetMapping("/test-error")
    public ResponseEntity<ApiResponse<?>> testError() {
        throw new RuntimeException("Test error");
    }
}