package com.example.demo.services.horarios;
import com.example.demo.dtos.citas.CitaResponseDTO;
import com.example.demo.dtos.horarios.FechaBloqueadaResponseDTO;
import com.example.demo.models.citas.CitaStatus;
import com.example.demo.services.citas.CitasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {
    private final CitasService citasService;
    private final HorariosService horariosService;
    @Override
    public List<CitaResponseDTO> listar(LocalDate date, YearMonth month, Integer limit, String status) {
        // 1. Obtener citas
        CitaStatus parsedStatus = (status != null) ? CitaStatus.valueOf(status.toUpperCase()) : null;
        List<CitaResponseDTO> citas = citasService.listar(date, month, limit, parsedStatus);
        // 2. Obtener bloqueadas del mismo período
        List<FechaBloqueadaResponseDTO> bloqueadas;
        if (date != null) {
            bloqueadas = horariosService.getBloqueadasByDate(date);
        } else if (month != null) {
            bloqueadas = horariosService.getBloqueadasByDateBetween(month.atDay(1), month.atEndOfMonth());
        } else {
            bloqueadas = horariosService.getBloqueadasByDate(LocalDate.now());
        }
        // 3. Convertir bloqueadas a CitaResponseDTO
        List<CitaResponseDTO> eventos = new ArrayList<>(citas);
        for (FechaBloqueadaResponseDTO b : bloqueadas) {
            CitaResponseDTO evento = CitaResponseDTO.builder()
                    .id("blocked-" + b.getId())
                    .date(b.getDate())
                    .time(b.getHoraInicio())
                    .duration(b.getHoraInicio() != null && b.getHoraFin() != null
                            ? "Bloqueado"
                            : "Todo el día")
                    .blocked(true)
                    .type(b.getReason() != null ? b.getReason() : "Sin motivo")
                    .typeColor("black")
                    .status("BLOQUEADO")
                    .statusColor("gray")
                    .build();
            eventos.add(evento);
        }
        // 4. Ordenar por hora
        eventos.sort((a, b) -> {
            if (a.getTime() == null && b.getTime() == null) return 0;
            if (a.getTime() == null) return 1;
            if (b.getTime() == null) return -1;
            return a.getTime().compareTo(b.getTime());
        });
        return eventos;
    }
}