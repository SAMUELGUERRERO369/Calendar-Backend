package com.example.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.example.demo.models.secuencias.Secuencia;

@Service
@RequiredArgsConstructor
public class SequenceService {

    private final MongoTemplate mongoTemplate;

    public long nextVal(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        
        Update update = new Update().inc("value", 1);
        
        Secuencia sequence = mongoTemplate.findAndModify(
            query,
            update,
            org.springframework.data.mongodb.core.FindAndModifyOptions.options()
                .upsert(true)
                .returnNew(true),
            Secuencia.class
        );
        
        return sequence.getValue();
    }
}
