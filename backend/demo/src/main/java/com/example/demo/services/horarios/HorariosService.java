package com.example.demo.services.horarios;

import com.example.demo.dtos.horarios.FechaBloqueadaRequestDTO;
import com.example.demo.dtos.horarios.FechaBloqueadaResponseDTO;
import com.example.demo.dtos.horarios.HorarioReglaRequestDTO;
import com.example.demo.dtos.horarios.HorarioReglaResponseDTO;

import java.util.List;

public interface HorariosService {
    List<HorarioReglaResponseDTO> getReglas();
    HorarioReglaResponseDTO crearRegla(HorarioReglaRequestDTO dto);
    HorarioReglaResponseDTO actualizarRegla(String id, HorarioReglaRequestDTO dto);
    void eliminarRegla(String id);
    List<FechaBloqueadaResponseDTO> getBloqueadas();
    FechaBloqueadaResponseDTO crearBloqueada(FechaBloqueadaRequestDTO dto);
    void eliminarBloqueada(String id);
}
