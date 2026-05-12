package com.example.demo.services.auth;

import com.example.demo.dtos.auth.LoginRequestDTO;
import com.example.demo.dtos.auth.LoginResponseDTO;
import com.example.demo.dtos.auth.RegisterRequestDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.auth.RefreshToken;
import com.example.demo.models.auth.User;
import com.example.demo.repositories.auth.AuthRepository;
import com.example.demo.repositories.auth.RefreshTokenRepository;
import com.example.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        // 1. Buscar User por username
        User user = authRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        // 2. Verificar password con BCrypt
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadRequestException("Credenciales inválidas");
        }

        // 3. Revocar refresh tokens anteriores del usuario (rotación de tokens)
        refreshTokenRepository.deleteByUsername(user.getUsername());

        // 4. Generar access token y refresh token
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        // 5. Guardar refresh token en la BD
        saveRefreshToken(refreshToken, user.getUsername());

        // 6. Devolver LoginResponseDTO con ambos tokens y datos del usuario
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(LoginResponseDTO.UsuarioLoginResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .name(user.getName())
                        .build())
                .build();
    }

    @Override
    public LoginResponseDTO register(RegisterRequestDTO dto) {
        // 1. Verificar que no exista User con ese username
        if (authRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BadRequestException("El usuario ya existe");
        }

        // 2. Hashear password
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        // 3. Crear User con createdAt = Instant.now()
        User newUser = User.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .name(dto.getName())
                .createdAt(Instant.now())
                .build();

        // 4. Guardar con AuthRepository
        authRepository.save(newUser);

        // 5. Llamar login() internamente y devolver el LoginResponseDTO
        LoginRequestDTO loginDto = LoginRequestDTO.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        return login(loginDto);
    }

    @Override
    public LoginResponseDTO refresh(String refreshToken) {
        // 1. Verificar que el refresh token sea válido
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new BadRequestException("Refresh token inválido o expirado");
        }

        // 2. Verificar que el refresh token esté en la BD y no esté revocado
        Optional<RefreshToken> storedToken = refreshTokenRepository.findByToken(refreshToken);
        if (storedToken.isEmpty() || storedToken.get().isRevoked()) {
            throw new BadRequestException("Refresh token no válido o ya revocado");
        }

        String username = jwtService.extractUsername(refreshToken);

        // 3. Revocar el refresh token anterior (rotación de seguridad)
        storedToken.get().setRevoked(true);
        refreshTokenRepository.save(storedToken.get());

        // 4. Generar nuevos tokens
        String newAccessToken = jwtService.generateAccessToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);

        // 5. Guardar el nuevo refresh token
        saveRefreshToken(newRefreshToken, username);

        // 6. Buscar usuario para devolver sus datos
        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        return LoginResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .user(LoginResponseDTO.UsuarioLoginResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .name(user.getName())
                        .build())
                .build();
    }

    @Override
    public void logout(String username) {
        // Revocar todos los refresh tokens del usuario
        refreshTokenRepository.deleteByUsername(username);
    }

    private void saveRefreshToken(String token, String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .username(username)
                .expiryDate(Instant.now().plusSeconds(604800)) // 7 días
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public String extractUsernameFromToken(String token) {
        return jwtService.extractUsername(token);
    }
}
