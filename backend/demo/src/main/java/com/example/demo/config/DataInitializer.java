package com.example.demo.config;

import com.example.demo.models.auth.User;
import com.example.demo.repositories.auth.AuthRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdminUser(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (authRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin1234"))
                        .name("Administrador")
                        .createdAt(Instant.now())
                        .build();
                authRepository.save(admin);
                System.out.println(">>> Usuario admin creado: admin / admin1234");
            }
        };
    }
}