package com.salon.auth_user_service.repository;

import com.salon.auth_user_service.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);
    void deleteAllByUserId(Long userId);
}
