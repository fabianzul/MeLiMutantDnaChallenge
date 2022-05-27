package com.meli.challenge.dna.repository;

import com.meli.challenge.dna.model.Dna;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DnaRepository extends MongoRepository<Dna, String> {
    //query to get statistics with project
    int countByType(Boolean type);
    boolean existsByDna(String[] dna);

}
