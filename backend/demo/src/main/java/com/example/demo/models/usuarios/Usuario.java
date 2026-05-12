package com.example.demo.models.usuarios;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")

public class Usuario {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String usuarioId; // Ej: #0042-AX
    private String status; // "active" | "inactive"
    private Instant createdAt;
    private Instant updatedAt;
}