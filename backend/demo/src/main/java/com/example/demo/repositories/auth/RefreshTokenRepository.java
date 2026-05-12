package com.example.demo.repositories.auth;

import com.example.demo.models.auth.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUsername(String username);
    void deleteByUsername(String username);
}