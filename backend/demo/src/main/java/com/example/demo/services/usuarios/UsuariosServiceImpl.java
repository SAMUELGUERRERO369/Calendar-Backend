package com.example.demo.services.usuarios;

import com.example.demo.dtos.usuarios.PageResponseDTO;
import com.example.demo.dtos.usuarios.UsuarioRequestDTO;
import com.example.demo.dtos.usuarios.UsuarioResponseDTO;
import com.example.demo.dtos.usuarios.UsuarioStatsDTO;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.usuarios.UsuarioMapper;
import com.example.demo.models.citas.CitaStatus;
import com.example.demo.models.usuarios.Usuario;
import com.example.demo.repositories.citas.CitaRepository;
import com.example.demo.repositories.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import com.example.demo.services.SequenceService;

@Service
@Primary
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final SequenceService sequenceService;
    private final CitaRepository citasRepository;

    @Override
    public PageResponseDTO<UsuarioResponseDTO> listar(int page, int limit, String search) {
        // Convertir de page-1 porque el frontend envía page desde 1
        Pageable pageable = Pageable.ofSize(limit).withPage(page - 1);
        
        Page<Usuario> usuariosPage;
        
        if (search != null && !search.trim().isEmpty()) {
            String searchPattern = search.trim();
            usuariosPage = usuarioRepository.findByNameContainingIgnoreCaseOrUsuarioIdContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(
                searchPattern, searchPattern, searchPattern, searchPattern, pageable);
        } else {
            usuariosPage = usuarioRepository.findAll(pageable);
        }
        
        PageResponseDTO<UsuarioResponseDTO> response = new PageResponseDTO<>();
        response.setData(usuarioMapper.toResponseList(usuariosPage.getContent()));
        response.setTotal(usuariosPage.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        
        return response;
    }

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        // Generar usuarioId único
        long seq = sequenceService.nextVal("usuario_seq");
        String sufijo = generarSufijo(2);
        String usuarioId = String.format("#%04d-%s", seq, sufijo);
        
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setUsuarioId(usuarioId);
        usuario.setStatus("inactive"); // No tiene citas aún
        usuario.setCreatedAt(Instant.now());
        usuario.setUpdatedAt(Instant.now());
        
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(saved);
    }

    @Override
    public UsuarioResponseDTO actualizar(String id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        
        usuario.setName(dto.getName());
        usuario.setEmail(dto.getEmail());
        usuario.setPhone(dto.getPhone());
        usuario.setUpdatedAt(Instant.now());
        
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(saved);
    }

    @Override
    public void eliminar(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private static String generarSufijo(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET[SECURE_RANDOM.nextInt(ALPHABET.length)]);
        }
        return builder.toString();
    }

    @Override
    public UsuarioStatsDTO stats() {
        UsuarioStatsDTO stats = new UsuarioStatsDTO();
        stats.setTotal((int) usuarioRepository.count());
        stats.setActive((int) usuarioRepository.countByStatus("active"));
        stats.setInactive((int) usuarioRepository.countByStatus("inactive"));
        return stats;
    }

    @Override
    public void sincronizarEstadoUsuario(String usuarioId) {
        // 1. Condición de Futuro (Engagement)
        long futuras = citasRepository.countByUsuarioIdAndStatusAndDateAfter(
            usuarioId, CitaStatus.CONFIRMADA, LocalDate.now());
        
        // 2. Condición de Frecuencia (90 días)
        LocalDate hace90Dias = LocalDate.now().minusDays(90);
        long recientes = citasRepository.countByUsuarioIdAndStatusAndDateAfter(
            usuarioId, CitaStatus.CONFIRMADA, hace90Dias);
        
        // 3. Decisión
        boolean esRecurrente = (futuras >= 1) || (recientes >= 2);
        String nuevoStatus = esRecurrente ? "active" : "inactive";
        
        // 4. Actualizar y Guardar
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        
        usuario.setStatus(nuevoStatus);
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.save(usuario);
    }
}
