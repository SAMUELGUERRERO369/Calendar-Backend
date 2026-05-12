package com.example.demo.mappers.citas;

import com.example.demo.models.citas.Cita;
import com.example.demo.models.citas.CitaType;
import com.example.demo.dtos.citas.CitaResponseDTO;
import com.example.demo.dtos.citas.CitaRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public abstract class CitaMapper {

    // Mapea de DTO a Entity (para crear/actualizar citas)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "date", source = "date", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "time", source = "time", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "type", source = "type", qualifiedByName = "stringToCitaType")
    public abstract Cita toEntity(CitaRequestDTO dto);

    @Named("stringToLocalDate")
    protected LocalDate stringToLocalDate(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    @Named("stringToLocalTime")
    protected LocalTime stringToLocalTime(String value) {
        return value == null ? null : LocalTime.parse(value, TIME_FORMATTER);
    }

    @Named("localDateToString")
    protected String localDateToString(LocalDate value) {
        return value == null ? null : value.toString();
    }

    @Named("localTimeToString")
    protected String localTimeToString(LocalTime value) {
        return value == null ? null : value.format(TIME_FORMATTER);
    }

    @Named("stringToCitaType")
    protected CitaType stringToCitaType(String value) {
        if (value == null) return null;
        try {
            return CitaType.valueOf(value.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    // Método Público que recibe los parámetros extra
    public CitaResponseDTO toResponseWithDetails(Cita cita, String usuarioNombre, String usuarioCodigo, String avatarUrl) {
        if (cita == null) return null;

        // 1. Mapeo base (ignoramos lo que seteamos a mano)
        CitaResponseDTO dto = toResponseInternal(cita);

        // 2. Duration: int -> "X min"
        if (cita.getDurationMinutes() > 0) {
            dto.setDuration(cita.getDurationMinutes() + " min");
        }

        // 3. Type & Color
        if (cita.getType() != null) {
            dto.setType(cita.getType().getDisplayName());
            dto.setTypeColor(cita.getType().getColor());
        }

        // 4. Status & Color
        if (cita.getStatus() != null) {
            dto.setStatus(cita.getStatus().getDisplayName());
            dto.setStatusColor(cita.getStatus().getColor());
        }

        // 5. Usuario embebido
        CitaResponseDTO.UsuarioCitaResponse userDto = new CitaResponseDTO.UsuarioCitaResponse();
        userDto.setName(usuarioNombre);
        userDto.setId(usuarioCodigo); // El #0042-AX
        userDto.setAvatar(avatarUrl);
        dto.setUsuario(userDto);

        return dto;
    }

    // Mapeo interno automático
    @Mapping(target = "date", source = "date", qualifiedByName = "localDateToString")
    @Mapping(target = "time", source = "time", qualifiedByName = "localTimeToString")
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "typeColor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "statusColor", ignore = true)
    protected abstract CitaResponseDTO toResponseInternal(Cita cita);

    protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
}
