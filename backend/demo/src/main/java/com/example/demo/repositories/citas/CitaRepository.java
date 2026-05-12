package com.example.demo.repositories.citas;

import com.example.demo.models.citas.Cita;
import com.example.demo.models.citas.CitaStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends MongoRepository<Cita, String> {
    List<Cita> findByDate(LocalDate date);
    List<Cita> findByDateBetween(LocalDate start, LocalDate end);
    List<Cita> findByStatus(CitaStatus status);
    List<Cita> findByDateAndStatus(LocalDate date, CitaStatus status);
    long countByUsuarioIdAndStatus(String usuarioId, CitaStatus status);
    long countByUsuarioIdAndStatusAndDateAfter(String usuarioId, CitaStatus status, LocalDate date);
}
