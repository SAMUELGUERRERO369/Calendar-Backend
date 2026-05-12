package com.example.demo.mappers.usuarios;

import com.example.demo.models.usuarios.Usuario;
import com.example.demo.dtos.usuarios.UsuarioRequestDTO;
import com.example.demo.dtos.usuarios.UsuarioResponseDTO;
import org.mapstruct.Mapper;
import java.util.List;

/**
 * Nombres coinciden: name, email, phone, status, avatar.
 * MapStruct mapea automáticamente. Cero @Mapping necesarios.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    // DTO -> Entity (Ignoramos id y timestamps que no vienen en el DTO)
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "avatar", ignore = true)
    @org.mapstruct.Mapping(target = "usuarioId", ignore = true)
    @org.mapstruct.Mapping(target = "status", ignore = true)
    @org.mapstruct.Mapping(target = "createdAt", ignore = true)
    @org.mapstruct.Mapping(target = "updatedAt", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    // Entity -> DTO (Ignoramos id y timestamps que no queremos exponer)
    UsuarioResponseDTO toResponse(Usuario entity);

    // List mapping (MapStruct auto-genera la implementación iterando toResponse)
    List<UsuarioResponseDTO> toResponseList(List<Usuario> entities);
}
