package com.example.demo.services.horarios;

import com.example.demo.dtos.horarios.FechaBloqueadaRequestDTO;
import com.example.demo.dtos.horarios.FechaBloqueadaResponseDTO;
import com.example.demo.dtos.horarios.HorarioReglaRequestDTO;
import com.example.demo.dtos.horarios.HorarioReglaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface HorariosService {

    List<HorarioReglaResponseDTO> getReglas();
    List<FechaBloqueadaResponseDTO> getBloqueadas();
    List<FechaBloqueadaResponseDTO> getBloqueadasByDate(LocalDate date);
    List<FechaBloqueadaResponseDTO> getBloqueadasByDateBetween(LocalDate start, LocalDate end);
    HorarioReglaResponseDTO crearRegla(HorarioReglaRequestDTO dto);
    HorarioReglaResponseDTO actualizarRegla(String id, HorarioReglaRequestDTO dto);
    FechaBloqueadaResponseDTO crearBloqueada(FechaBloqueadaRequestDTO dto);
    void eliminarBloqueada(String id);
    void eliminarRegla(String id);

}
