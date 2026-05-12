package com.example.demo.models.horarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "horario_reglas")
public class HorarioRegla {
    @Id
    private String id;
    private String dayOfWeek; // monday, tuesday...
    private String horaInicio; 
    private String horaFin;
    @Builder.Default
    private List<Pausa> pausas = new ArrayList<>();
}