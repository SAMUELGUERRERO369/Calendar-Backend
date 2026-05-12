package com.example.demo.repositories.secuencias;

import com.example.demo.models.secuencias.Secuencia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecuenciaRepository extends MongoRepository<Secuencia, String> {
    // No custom methods needed
}
