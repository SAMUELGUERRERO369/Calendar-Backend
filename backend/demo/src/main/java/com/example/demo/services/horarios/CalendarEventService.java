package com.example.demo.services.horarios;
import com.example.demo.dtos.citas.CitaResponseDTO;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface CalendarEventService {
    List<CitaResponseDTO> listar(LocalDate date, YearMonth month, Integer limit, String status);
}