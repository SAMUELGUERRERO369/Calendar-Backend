package com.example.demo.repositories.horarios;

import com.example.demo.models.horarios.FechaBloqueada;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FechaBloqueadaRepository extends MongoRepository<FechaBloqueada, String> {
    // No custom methods needed
}
