package com.example.demo.dtos.horarios;
import jakarta.validation.constraints.AssertTrue;
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
    private String horaInicio; // HH:mm, optional but must come with horaFin
    private String horaFin;    // HH:mm, optional but must come with horaInicio

    @AssertTrue(message = "horaInicio y horaFin deben estar ambos presentes o ambos ausentes")
    public boolean isHoraInicioHoraFinConsistent() {
        return (horaInicio == null) == (horaFin == null);
    }
}