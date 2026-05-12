package com.example.demo.controllers.horarios;

import com.example.demo.dtos.horarios.FechaBloqueadaRequestDTO;
import com.example.demo.dtos.horarios.FechaBloqueadaResponseDTO;
import com.example.demo.dtos.horarios.HorarioReglaRequestDTO;
import com.example.demo.dtos.horarios.HorarioReglaResponseDTO;
import com.example.demo.services.horarios.HorariosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorariosController {

    private final HorariosService horariosService;

    // Endpoints para HorarioRegla
    @GetMapping("/reglas")
    public ResponseEntity<List<HorarioReglaResponseDTO>> getReglas() {
        List<HorarioReglaResponseDTO> response = horariosService.getReglas();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reglas")
    public ResponseEntity<HorarioReglaResponseDTO> crearRegla(@Valid @RequestBody HorarioReglaRequestDTO dto) {
        HorarioReglaResponseDTO response = horariosService.crearRegla(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/reglas/{id}")
    public ResponseEntity<HorarioReglaResponseDTO> actualizarRegla(
            @PathVariable String id,
            @Valid @RequestBody HorarioReglaRequestDTO dto) {
        HorarioReglaResponseDTO response = horariosService.actualizarRegla(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reglas/{id}")
    public ResponseEntity<Void> eliminarRegla(@PathVariable String id) {
        horariosService.eliminarRegla(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para FechaBloqueada
    @GetMapping("/bloqueadas")
    public ResponseEntity<List<FechaBloqueadaResponseDTO>> getBloqueadas() {
        List<FechaBloqueadaResponseDTO> response = horariosService.getBloqueadas();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bloqueadas")
    public ResponseEntity<FechaBloqueadaResponseDTO> crearBloqueada(@Valid @RequestBody FechaBloqueadaRequestDTO dto) {
        FechaBloqueadaResponseDTO response = horariosService.crearBloqueada(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/bloqueadas/{id}")
    public ResponseEntity<Void> eliminarBloqueada(@PathVariable String id) {
        horariosService.eliminarBloqueada(id);
        return ResponseEntity.noContent().build();
    }
}
