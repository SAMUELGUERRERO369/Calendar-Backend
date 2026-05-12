package com.example.demo.controllers.usuarios;

import com.example.demo.dtos.usuarios.PageResponseDTO;
import com.example.demo.dtos.usuarios.UsuarioRequestDTO;
import com.example.demo.dtos.usuarios.UsuarioResponseDTO;
import com.example.demo.dtos.usuarios.UsuarioStatsDTO;
import com.example.demo.services.usuarios.UsuariosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;

    @GetMapping("/stats")
    public ResponseEntity<UsuarioStatsDTO> getStats() {
        UsuarioStatsDTO stats = usuariosService.stats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<UsuarioResponseDTO>> listar(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        
        PageResponseDTO<UsuarioResponseDTO> response = usuariosService.listar(page, limit, search);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuariosService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        
        UsuarioResponseDTO response = usuariosService.actualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuariosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
