package com.example.demo.models.horarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor

@AllArgsConstructor
public class Pausa {
    private LocalTime inicio;
    private LocalTime fin;
}