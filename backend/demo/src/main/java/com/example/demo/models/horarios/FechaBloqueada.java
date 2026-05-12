package com.example.demo.models.horarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "fechas_bloqueadas")
public class FechaBloqueada {
    @Id
    private String id;
    private LocalDate date;
    private String reason;
}