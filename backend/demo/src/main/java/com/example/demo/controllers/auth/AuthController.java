package com.example.demo.controllers.auth;

import com.example.demo.dtos.auth.LoginRequestDTO;
import com.example.demo.dtos.auth.LoginResponseDTO;
import com.example.demo.dtos.auth.RegisterRequestDTO;
import com.example.demo.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid RegisterRequestDTO dto) {
        LoginResponseDTO response = authService.register(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody RefreshRequestDTO dto) {
        LoginResponseDTO response = authService.refresh(dto.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        // Extraer username del access token (Bearer <token>)
        String token = authHeader.substring(7);
        String username = authService.extractUsernameFromToken(token);
        authService.logout(username);
        return ResponseEntity.noContent().build();
    }

    // DTO interno para el request de refresh
    @lombok.Data
    public static class RefreshRequestDTO {
        private String refreshToken;
    }
}
