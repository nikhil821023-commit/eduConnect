package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_revoked")
    private boolean isRevoked;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructor
    public RefreshToken() {}

    public RefreshToken(String userId, String token,
                        String userRole, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.userRole = userRole;
        this.expiresAt = expiresAt;
        this.isRevoked = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getToken() { return token; }
    public String getUserRole() { return userRole; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return isRevoked; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setToken(String token) { this.token = token; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setRevoked(boolean revoked) { isRevoked = revoked; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}