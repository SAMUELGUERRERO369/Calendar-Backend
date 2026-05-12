package com.example.demo.dtos.citas;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaRequestDTO {
    // Todos opcionales para permitir update parcial
    private String usuarioId; // ObjectId del usuario
    private String date; // Format YYYY-MM-DD
    private String time; // Format HH:mm
    @Min(5)
    @Max(480)
    private Integer durationMinutes;
    private String type; // String to be parsed to CitaType
    private String status; // Optional: CONFIRMADA | PENDIENTE | CANCELADA (solo para update)
}
