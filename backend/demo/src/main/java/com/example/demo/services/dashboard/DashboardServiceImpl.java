package com.example.demo.services.dashboard;

import com.example.demo.dtos.dashboard.DashboardStatsDTO;
import com.example.demo.models.citas.Cita;
import com.example.demo.models.citas.CitaStatus;
import com.example.demo.models.usuarios.Usuario;
import com.example.demo.repositories.citas.CitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CitaRepository citasRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public DashboardStatsDTO getStats() {
        LocalDate today = LocalDate.now();

        // Total de citas hoy
        List<Cita> citasHoy = citasRepository.findByDate(today);
        int totalCitas = citasHoy.size();

        // Citas por estado (solo las de hoy)
        List<Cita> citasConfirmadasList = citasRepository.findByDateAndStatus(today, CitaStatus.CONFIRMADA);
        List<Cita> citasPendientesList = citasRepository.findByDateAndStatus(today, CitaStatus.PENDIENTE);
        List<Cita> citasCanceladasList = citasRepository.findByDateAndStatus(today, CitaStatus.CANCELADA);

        int citasConfirmadas = citasConfirmadasList.size();
        int citasPendientes = citasPendientesList.size();
        int citasCanceladas = citasCanceladasList.size();

        // Nuevos pacientes de la semana actual
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        Instant weekStart = monday.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant now = Instant.now();

        Query query = new Query(Criteria.where("createdAt").gte(weekStart).lte(now));
        long newPatients = mongoTemplate.count(query, Usuario.class);

        // Calcular tendencia vs ayer
        LocalDate yesterday = today.minusDays(1);
        List<Cita> citasAyerList = citasRepository.findByDate(yesterday);
        int citasAyer = citasAyerList.size();

        String trend;
        if (citasAyer == 0) {
            trend = totalCitas > 0 ? "+100% vs ayer" : "Sin datos de ayer";
        } else {
            int diff = ((totalCitas - citasAyer) * 100) / citasAyer;
            trend = (diff >= 0 ? "+" : "") + diff + "% vs ayer";
        }

        // Construir stats
        return DashboardStatsDTO.builder()
                .totalCitas(totalCitas)
                .usuariosActivos((int) newPatients)
                .citasConfirmadas(citasConfirmadas)
                .citasPendientes(citasPendientes)
                .citasCanceladas(citasCanceladas)
                .appointmentsTrend(trend)
                .centerStatus("Sistema operativo.")
                .capacity(0)
                .build();
    }
}
