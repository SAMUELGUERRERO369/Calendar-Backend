package com.example.demo.services.citas;

import com.example.demo.dtos.citas.CitaRequestDTO;
import com.example.demo.dtos.citas.CitaResponseDTO;
import com.example.demo.models.citas.CitaStatus;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface CitasService {
    List<CitaResponseDTO> listar(LocalDate date, YearMonth month, Integer limit, CitaStatus status);
    CitaResponseDTO crear(CitaRequestDTO dto);
    CitaResponseDTO actualizar(String id, CitaRequestDTO dto);
    void eliminar(String id);
}
