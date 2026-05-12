package com.example.demo.services.usuarios;

import com.example.demo.dtos.usuarios.PageResponseDTO;
import com.example.demo.dtos.usuarios.UsuarioRequestDTO;
import com.example.demo.dtos.usuarios.UsuarioResponseDTO;
import com.example.demo.dtos.usuarios.UsuarioStatsDTO;

public interface UsuariosService {
    PageResponseDTO<UsuarioResponseDTO> listar(int page, int limit, String search);
    UsuarioResponseDTO crear(UsuarioRequestDTO dto);
    UsuarioResponseDTO actualizar(String id, UsuarioRequestDTO dto);
    void eliminar(String id);
    UsuarioStatsDTO stats();
    void sincronizarEstadoUsuario(String usuarioId);
}
