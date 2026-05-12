package com.example.demo.dtos.horarios;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FechaBloqueadaRequestDTO {
    @NotBlank(message = "La fecha no puede estar vacía")
    private String date; // YYYY-MM-DD
    private String reason; // Optional
}