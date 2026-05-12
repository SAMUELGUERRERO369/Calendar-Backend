package com.example.demo.dtos.usuarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioStatsDTO {
    private int total;
    private int active;      // Antes: activePlans
    private int inactive;    // Antes: pending
}