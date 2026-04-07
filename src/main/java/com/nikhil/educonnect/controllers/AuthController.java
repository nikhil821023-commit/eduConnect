package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponse>> register(
            @RequestBody RegisterRequest request) {

        LoginResponse response = authService.register(request);
        return ResponseEntity.ok(
                ApiResponse.success(response,
                        "Registration successful! Welcome to EduConnect+")
        );
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.success(response,
                        "Login successful! Welcome back, "
                                + response.getName())
        );
    }

    // POST /api/auth/refresh
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(
            @RequestBody RefreshTokenRequest request) {

        LoginResponse response = authService.refresh(request);
        return ResponseEntity.ok(
                ApiResponse.success(response,
                        "Token refreshed successfully")
        );
    }
}