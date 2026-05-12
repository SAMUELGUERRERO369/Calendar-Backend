package com.example.demo.dtos.dashboard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private int totalCitas;
    private int usuariosActivos;
    private int citasConfirmadas;
    private int citasPendientes;
    private int citasCanceladas;
    private String appointmentsTrend;
    private String centerStatus;
    private int capacity;
}
