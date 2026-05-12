package com.example.demo.dtos.horarios;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioReglaRequestDTO {
    @NotBlank
    private String dayOfWeek; // monday, tuesday...
    @NotBlank
    private String horaInicio; // Antes: startTime
    @NotBlank
    private String horaFin; // Antes: endTime
    @Builder.Default
    private List<PausaDTO> pausas = new ArrayList<>(); // Antes: breaks
}