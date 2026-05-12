package com.example.demo.models.citas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "citas")
public class Cita {
    @Id
    private String id;
    private LocalDate date;
    private LocalTime time;
    private int durationMinutes;
    private String usuarioId; // ObjectId del usuario
    private CitaType type;
    private CitaStatus status;
    private Instant createdAt;
}