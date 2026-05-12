package com.example.demo.models.secuencias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "sequences")
public class Secuencia {
    @Id
    private String id; // ej: "usuario_seq"
    private long value;
}