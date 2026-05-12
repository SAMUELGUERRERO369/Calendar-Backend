package com.example.demo.dtos.usuarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private String id;
    private String name;
    private String usuarioId; // Ej: #0042-AX
    private String email;
    private String phone;
    private String avatar; // nullable
    private String status; // "active" | "inactive"
}