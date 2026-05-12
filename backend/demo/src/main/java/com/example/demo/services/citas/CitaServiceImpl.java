package com.example.demo.services.citas;

import com.example.demo.dtos.citas.CitaRequestDTO;
import com.example.demo.dtos.citas.CitaResponseDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.citas.CitaMapper;
import com.example.demo.models.citas.Cita;
import com.example.demo.models.citas.CitaStatus;
import com.example.demo.models.citas.CitaType;
import com.example.demo.models.usuarios.Usuario;
import com.example.demo.repositories.citas.CitaRepository;
import com.example.demo.repositories.usuarios.UsuarioRepository;
import com.example.demo.services.usuarios.UsuariosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class CitaServiceImpl implements CitasService {

	private final CitaRepository citaRepository;
	private final UsuarioRepository usuarioRepository;
	private final CitaMapper citaMapper;
	private final UsuariosService usuariosService;

	@Override
	public List<CitaResponseDTO> listar(LocalDate date, YearMonth month, Integer limit, CitaStatus status) {
		List<Cita> citas;

		if (date != null) {
			citas = status == null
					? citaRepository.findByDate(date)
					: citaRepository.findByDateAndStatus(date, status);
		} else if (month != null) {
			LocalDate start = month.atDay(1);
			LocalDate end = month.atEndOfMonth();
			citas = citaRepository.findByDateBetween(start, end);
			if (status != null) {
				citas = citas.stream()
						.filter(cita -> status.equals(cita.getStatus()))
						.toList();
			}
		} else {
			LocalDate today = LocalDate.now();
			citas = status == null
					? citaRepository.findByDate(today)
					: citaRepository.findByDateAndStatus(today, status);
		}

		if (limit != null && limit > 0 && citas.size() > limit) {
			citas = citas.subList(0, limit);
		}

		return citas.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public CitaResponseDTO crear(CitaRequestDTO dto) {
		Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
				.orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

		Cita cita = citaMapper.toEntity(dto);
		cita.setStatus(CitaStatus.PENDIENTE);
		cita.setCreatedAt(Instant.now());

		Cita saved = citaRepository.save(cita);
		return citaMapper.toResponseWithDetails(saved, usuario.getName(), usuario.getUsuarioId(), usuario.getAvatar());
	}

	@Override
	public CitaResponseDTO actualizar(String id, CitaRequestDTO dto) {
		Cita cita = citaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cita no encontrada"));

		// Si no se envía usuarioId, preservar el original
		String usuarioId = (dto.getUsuarioId() != null && !dto.getUsuarioId().isBlank())
				? dto.getUsuarioId()
				: cita.getUsuarioId();

		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

		cita.setUsuarioId(usuarioId);
		
		// Preservar valores originales si no se envían
		if (dto.getDate() != null && !dto.getDate().isBlank()) {
			cita.setDate(LocalDate.parse(dto.getDate()));
		}
		if (dto.getTime() != null && !dto.getTime().isBlank()) {
			cita.setTime(LocalTime.parse(dto.getTime()));
		}
		if (dto.getDurationMinutes() != null) {
			cita.setDurationMinutes(dto.getDurationMinutes());
		}
		
		// Type es requerido
		if (dto.getType() != null && !dto.getType().isBlank()) {
			cita.setType(CitaType.valueOf(dto.getType().toUpperCase().replace(" ", "_")));
		}
		
		// Status es opcional
		if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
			try {
				cita.setStatus(CitaStatus.valueOf(dto.getStatus().trim().toUpperCase()));
			} catch (IllegalArgumentException ex) {
				throw new BadRequestException("Estado invalido");
			}
		}

		Cita saved = citaRepository.save(cita);

		if (saved.getStatus() == CitaStatus.CONFIRMADA || saved.getStatus() == CitaStatus.CANCELADA) {
			usuariosService.sincronizarEstadoUsuario(saved.getUsuarioId());
		}

		return citaMapper.toResponseWithDetails(saved, usuario.getName(), usuario.getUsuarioId(), usuario.getAvatar());
	}

	@Override
	public void eliminar(String id) {
		Cita cita = citaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cita no encontrada"));

		String usuarioId = cita.getUsuarioId();
		citaRepository.deleteById(id);
		usuariosService.sincronizarEstadoUsuario(usuarioId);
	}

	private CitaResponseDTO mapToResponse(Cita cita) {
		Usuario usuario = usuarioRepository.findById(cita.getUsuarioId())
				.orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		return citaMapper.toResponseWithDetails(cita, usuario.getName(), usuario.getUsuarioId(), usuario.getAvatar());
	}
}
