package com.example.demo.dtos.horarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FechaBloqueadaResponseDTO {
    private String id;
    private String date;
    private String reason;
    private String horaInicio; // HH:mm
    private String horaFin;    // HH:mm
}