package com.example.demo.dtos.horarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PausaDTO { // Antes: BreakDTO
    private String inicio; // Antes: start (HH:mm)
    private String fin; // Antes: end (HH:mm)
}