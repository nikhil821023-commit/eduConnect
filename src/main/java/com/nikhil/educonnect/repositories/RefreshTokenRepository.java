package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    // Revoke all tokens for a user on logout
    @Transactional
    void deleteByUserId(String userId);
}