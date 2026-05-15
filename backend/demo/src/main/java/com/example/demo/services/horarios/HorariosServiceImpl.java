package com.example.demo.services.horarios;

import com.example.demo.dtos.horarios.FechaBloqueadaRequestDTO;
import com.example.demo.dtos.horarios.FechaBloqueadaResponseDTO;
import com.example.demo.dtos.horarios.HorarioReglaRequestDTO;
import com.example.demo.dtos.horarios.HorarioReglaResponseDTO;
import com.example.demo.dtos.horarios.PausaDTO;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.horarios.HorarioMapper;
import com.example.demo.models.horarios.FechaBloqueada;
import com.example.demo.models.horarios.HorarioRegla;
import com.example.demo.models.horarios.Pausa;
import com.example.demo.repositories.horarios.FechaBloqueadaRepository;
import com.example.demo.repositories.horarios.HorarioReglaRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HorariosServiceImpl implements HorariosService {

    private final HorarioReglaRepository horarioReglaRepository;
    private final FechaBloqueadaRepository fechaBloqueadaRepository;
    private final HorarioMapper horarioMapper;

    @Override
    public List<HorarioReglaResponseDTO> getReglas() {
        List<HorarioRegla> reglas = horarioReglaRepository.findAll();
        return horarioMapper.toHorarioReglaResponseDTOList(reglas);
    }

    @Override
    public HorarioReglaResponseDTO crearRegla(HorarioReglaRequestDTO dto) {
        // Asegurar que pausas no sea null
        if (dto.getPausas() == null) {
            dto.setPausas(new ArrayList<>());
        }
        HorarioRegla entity = horarioMapper.toEntity(dto);
        // Asegurar que pausas en la entidad no sea null
        if (entity.getPausas() == null) {
            entity.setPausas(new ArrayList<>());
        }
        HorarioRegla saved = horarioReglaRepository.save(entity);
        return horarioMapper.toHorarioReglaResponseDTO(saved);
    }

    @Override
    public HorarioReglaResponseDTO actualizarRegla(String id, HorarioReglaRequestDTO dto) {
        HorarioRegla existing = horarioReglaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("HorarioRegla not found with id: " + id));
        
        // Actualizar campos
        existing.setDayOfWeek(dto.getDayOfWeek());
        existing.setHoraInicio(dto.getHoraInicio());
        existing.setHoraFin(dto.getHoraFin());
        
        // Manejar pausas
        List<PausaDTO> pausasDto = dto.getPausas();
        if (pausasDto == null) {
            existing.setPausas(new ArrayList<>());
        } else {
            List<Pausa> pausas = new ArrayList<>();
            for (PausaDTO pausaDto : pausasDto) {
                pausas.add(horarioMapper.toPausa(pausaDto));
            }
            existing.setPausas(pausas);
        }
        
        HorarioRegla saved = horarioReglaRepository.save(existing);
        return horarioMapper.toHorarioReglaResponseDTO(saved);
    }

    @Override
    public void eliminarRegla(String id) {
        if (!horarioReglaRepository.existsById(id)) {
            throw new NotFoundException("HorarioRegla not found with id: " + id);
        }
        horarioReglaRepository.deleteById(id);
    }

    @Override
    public List<FechaBloqueadaResponseDTO> getBloqueadas() {
        List<FechaBloqueada> bloqueadas = fechaBloqueadaRepository.findAll();
        return horarioMapper.toFechaBloqueadaResponseDTOList(bloqueadas);
    }

    @Override
    public List<FechaBloqueadaResponseDTO> getBloqueadasByDate(LocalDate date) {
        List<FechaBloqueada> bloqueadas = fechaBloqueadaRepository.findByDate(date);
        return horarioMapper.toFechaBloqueadaResponseDTOList(bloqueadas);
    }
    
    @Override
    public List<FechaBloqueadaResponseDTO> getBloqueadasByDateBetween(LocalDate start, LocalDate end) {
        List<FechaBloqueada> bloqueadas = fechaBloqueadaRepository.findByDateBetween(start, end);
        return horarioMapper.toFechaBloqueadaResponseDTOList(bloqueadas);
    }

    @Override
    public FechaBloqueadaResponseDTO crearBloqueada(FechaBloqueadaRequestDTO dto) {
        FechaBloqueada entity = horarioMapper.toEntity(dto);
        // Parse date from String if needed (mapper should handle it)
        FechaBloqueada saved = fechaBloqueadaRepository.save(entity);
        return horarioMapper.toFechaBloqueadaResponseDTO(saved);
    }

    @Override
    public void eliminarBloqueada(String id) {
        if (!fechaBloqueadaRepository.existsById(id)) {
            throw new NotFoundException("FechaBloqueada not found with id: " + id);
        }
        fechaBloqueadaRepository.deleteById(id);
    }
}
