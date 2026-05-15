package com.example.demo.repositories.horarios;

import com.example.demo.models.horarios.FechaBloqueada;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface FechaBloqueadaRepository extends MongoRepository<FechaBloqueada, String> {

    List<FechaBloqueada> findByDate(LocalDate date);
    List<FechaBloqueada> findByDateBetween(LocalDate start, LocalDate end);

    // No custom methods needed
}
