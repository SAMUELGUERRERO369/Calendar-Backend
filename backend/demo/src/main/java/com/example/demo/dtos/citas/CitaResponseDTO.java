package com.example.demo.dtos.citas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponseDTO {
    private String id;
    private String date; // String YYYY-MM-DD
    private String time; // String HH:mm
    private String duration; // "30 min" (calculated by Mapper)
    
    // Embedded object for Usuario info
    private UsuarioCitaResponse usuario; 
    
    private String type; // Display name: "Revisión General"
    private String typeColor; // "teal"
    private String status; // Display name: "Confirmada"
    private String statusColor; // "green"
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioCitaResponse {
        private String name;
        private String id; // The usuarioId code (#0042-AX)
        private String avatar; // nullable
    }
}