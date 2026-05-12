package com.example.demo.services.auth;

import com.example.demo.dtos.auth.LoginRequestDTO;
import com.example.demo.dtos.auth.LoginResponseDTO;
import com.example.demo.dtos.auth.RegisterRequestDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO dto);
    LoginResponseDTO register(RegisterRequestDTO dto);
    LoginResponseDTO refresh(String refreshToken);
    void logout(String username);
    String extractUsernameFromToken(String token);
}
