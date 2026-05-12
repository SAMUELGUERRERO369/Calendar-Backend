package com.example.demo.controllers.citas;

import com.example.demo.dtos.citas.CitaRequestDTO;
import com.example.demo.dtos.citas.CitaResponseDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.citas.CitaStatus;
import com.example.demo.services.citas.CitasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitasController {

	private final CitasService citasService;

	@GetMapping
	public ResponseEntity<List<CitaResponseDTO>> listar(
			@RequestParam(required = false) String date,
			@RequestParam(required = false) String month,
			@RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String status
	) {
		LocalDate parsedDate = parseDate(date);
		YearMonth parsedMonth = parsedDate == null ? parseMonth(month) : null;
		CitaStatus parsedStatus = parseStatus(status);

		List<CitaResponseDTO> response = citasService.listar(parsedDate, parsedMonth, limit, parsedStatus);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<CitaResponseDTO> crear(@Valid @org.springframework.web.bind.annotation.RequestBody CitaRequestDTO dto) {
		CitaResponseDTO response = citasService.crear(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CitaResponseDTO> actualizar(
			@PathVariable String id,
			@Valid @org.springframework.web.bind.annotation.RequestBody CitaRequestDTO dto
	) {
		CitaResponseDTO response = citasService.actualizar(id, dto);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable String id) {
		citasService.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	private LocalDate parseDate(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException ex) {
			throw new BadRequestException("Fecha invalida. Use formato YYYY-MM-DD");
		}
	}

	private YearMonth parseMonth(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return YearMonth.parse(value);
		} catch (DateTimeParseException ex) {
			throw new BadRequestException("Mes invalido. Use formato YYYY-MM");
		}
	}

	private CitaStatus parseStatus(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return CitaStatus.valueOf(value.trim().toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new BadRequestException("Estado invalido");
		}
	}
}
