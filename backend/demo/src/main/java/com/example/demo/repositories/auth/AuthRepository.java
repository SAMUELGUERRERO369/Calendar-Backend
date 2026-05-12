package com.example.demo.repositories.auth;

import com.example.demo.models.auth.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AuthRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
