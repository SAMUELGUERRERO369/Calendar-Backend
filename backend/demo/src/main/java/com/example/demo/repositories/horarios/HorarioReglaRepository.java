package com.example.demo.repositories.horarios;

import com.example.demo.models.horarios.HorarioRegla;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HorarioReglaRepository extends MongoRepository<HorarioRegla, String> {
    // No custom methods needed
}
