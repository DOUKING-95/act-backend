package com.health_donate.health.repository;



import com.health_donate.health.entity.RefreshToken;
import com.health_donate.health.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(Actor user);
}

