package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.exceptions.UserAlreadyExistsException;
import com.nikhil.educonnect.models.*;
import com.nikhil.educonnect.repositories.*;
import com.nikhil.educonnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    // ─── REGISTER ─────────────────────────────────────────
    public LoginResponse register(RegisterRequest request) {

        String role = request.getRole().toUpperCase();
        String userId;
        String name;

        switch (role) {

            case "TEACHER" -> {
                if (teacherRepository.existsByEmail(
                        request.getEmail())) {
                    throw new UserAlreadyExistsException(
                            request.getEmail()
                    );
                }
                Teacher teacher = new Teacher(
                        request.getName(),
                        request.getEmail(),
                        passwordEncoder.encode(request.getPassword()),
                        request.getSubject(),
                        request.getExperienceYears(),
                        request.getCity()
                );
                Teacher saved = teacherRepository.save(teacher);
                userId = saved.getId();
                name = saved.getName();
            }

            case "SCHOOL" -> {
                if (schoolRepository.existsByEmail(
                        request.getEmail())) {
                    throw new UserAlreadyExistsException(
                            request.getEmail()
                    );
                }
                School school = new School(
                        request.getSchoolName(),
                        request.getEmail(),
                        passwordEncoder.encode(request.getPassword()),
                        request.getAddress(),
                        request.getCity(),
                        request.getBoard()
                );
                School saved = schoolRepository.save(school);
                userId = saved.getId();
                name = saved.getSchoolName();
            }

            default -> throw new RuntimeException(
                    "Invalid role. Use: TEACHER or SCHOOL"
            );
        }

        return buildLoginResponse(
                userId, name, request.getEmail(), role
        );
    }

    // ─── LOGIN ────────────────────────────────────────────
    public LoginResponse login(LoginRequest request) {

        String role = request.getRole().toUpperCase();
        String userId;
        String name;
        String storedPassword;

        switch (role) {

            case "TEACHER" -> {
                Teacher teacher = teacherRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email or password"
                                )
                        );
                storedPassword = teacher.getPassword();
                userId = teacher.getId();
                name = teacher.getName();
            }

            case "SCHOOL" -> {
                School school = schoolRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email or password"
                                )
                        );
                storedPassword = school.getPassword();
                userId = school.getId();
                name = school.getSchoolName();
            }

            default -> throw new RuntimeException(
                    "Invalid role. Use: TEACHER or SCHOOL"
            );
        }

        // Verify password using BCrypt
        if (!passwordEncoder.matches(
                request.getPassword(), storedPassword)) {
            throw new RuntimeException("Invalid email or password");
        }

        return buildLoginResponse(
                userId, name, request.getEmail(), role
        );
    }

    // ─── REFRESH TOKEN ────────────────────────────────────
    public LoginResponse refresh(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token")
                );

        // Check if revoked
        if (refreshToken.isRevoked()) {
            throw new RuntimeException(
                    "Refresh token has been revoked"
            );
        }

        // Check if expired
        if (refreshToken.getExpiresAt()
                .isBefore(LocalDateTime.now())) {
            throw new RuntimeException(
                    "Refresh token has expired. Please login again."
            );
        }

        // Generate new access token
        String newAccessToken = jwtUtil.generateAccessToken(
                refreshToken.getUserId(),
                refreshToken.getUserRole()
        );

        LoginResponse response = new LoginResponse();
        response.setUserId(refreshToken.getUserId());
        response.setRole(refreshToken.getUserRole());
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(request.getRefreshToken());
        return response;
    }

    // ─── HELPER — Build Login Response ───────────────────
    private LoginResponse buildLoginResponse(
            String userId, String name,
            String email, String role) {

        // Generate access token (15 min)
        String accessToken = jwtUtil.generateAccessToken(
                userId, role
        );

        // Generate refresh token (7 days)
        String refreshTokenValue = UUID.randomUUID().toString();

        // Delete old refresh tokens for this user
        refreshTokenRepository.deleteByUserId(userId);

        // Save new refresh token
        RefreshToken refreshToken = new RefreshToken(
                userId,
                refreshTokenValue,
                role,
                LocalDateTime.now().plusSeconds(
                        refreshExpiration / 1000
                )
        );
        refreshTokenRepository.save(refreshToken);

        // Build response
        LoginResponse response = new LoginResponse();
        response.setUserId(userId);
        response.setName(name);
        response.setEmail(email);
        response.setRole(role);
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshTokenValue);
        return response;
    }
}