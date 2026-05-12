package com.example.demo.dtos.horarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioReglaResponseDTO {
    private String id;
    private String dayOfWeek;
    private String horaInicio;
    private String horaFin;
    private List<PausaDTO> pausas;
}