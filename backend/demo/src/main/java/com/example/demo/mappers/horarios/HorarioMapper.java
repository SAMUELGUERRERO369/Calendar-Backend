package com.example.demo.mappers.horarios;

import com.example.demo.dtos.horarios.FechaBloqueadaRequestDTO;
import com.example.demo.dtos.horarios.FechaBloqueadaResponseDTO;
import com.example.demo.dtos.horarios.HorarioReglaRequestDTO;
import com.example.demo.dtos.horarios.HorarioReglaResponseDTO;
import com.example.demo.dtos.horarios.PausaDTO;
import com.example.demo.models.horarios.FechaBloqueada;
import com.example.demo.models.horarios.HorarioRegla;
import com.example.demo.models.horarios.Pausa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class HorarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pausas", source = "pausas")
    public abstract HorarioRegla toEntity(HorarioReglaRequestDTO dto);

    @Mapping(target = "pausas", source = "pausas")
    public abstract HorarioReglaResponseDTO toHorarioReglaResponseDTO(HorarioRegla entity);

    public abstract List<HorarioReglaResponseDTO> toHorarioReglaResponseDTOList(List<HorarioRegla> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", source = "date", qualifiedByName = "stringToLocalDate")
    public abstract FechaBloqueada toEntity(FechaBloqueadaRequestDTO dto);

    @Mapping(target = "date", source = "date", qualifiedByName = "localDateToString")
    public abstract FechaBloqueadaResponseDTO toFechaBloqueadaResponseDTO(FechaBloqueada entity);

    public abstract List<FechaBloqueadaResponseDTO> toFechaBloqueadaResponseDTOList(List<FechaBloqueada> entities);

    @Mapping(target = "inicio", source = "inicio", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "fin", source = "fin", qualifiedByName = "stringToLocalTime")
    public abstract Pausa toPausa(PausaDTO dto);

    @Mapping(target = "inicio", source = "inicio", qualifiedByName = "localTimeToString")
    @Mapping(target = "fin", source = "fin", qualifiedByName = "localTimeToString")
    public abstract PausaDTO toPausaDTO(Pausa entity);

    @Named("stringToLocalDate")
    protected LocalDate stringToLocalDate(String value) {
        return value == null || value.isBlank() ? null : LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Named("localDateToString")
    protected String localDateToString(LocalDate value) {
        return value == null ? null : value.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Named("stringToLocalTime")
    protected LocalTime stringToLocalTime(String value) {
        return value == null || value.isBlank() ? null : LocalTime.parse(value, TIME_FORMATTER);
    }

    @Named("localTimeToString")
    protected String localTimeToString(LocalTime value) {
        return value == null ? null : value.format(TIME_FORMATTER);
    }

    protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
}
