package com.meli.challenge.dna.service;

import com.meli.challenge.dna.dto.StatsDTO;
import com.meli.challenge.dna.model.Dna;
import org.springframework.data.domain.Example;

import java.util.List;

public interface DnaService {

    /**
     * Método que retorna una lista con la información de todos las secuencias de DNA que se han verificado.
     *
     * @return List<Dna>
     */
    List<Dna> findAll();

    /**
     * Método para evaluar si la secuencia de DNA ya fue verificada anteriormente.
     *
     * @param dna
     * @return boolean
     */
    boolean existsByDna(String[] dna);

    /**
     * Método para guardar la información de una secuencia de DNA
     *
     * @param dna
     * @return Dna
     */
    Dna saveOrUpdateDna(Dna dna);

    /**
     * Método que retorna la cantidad de mutantes o humanos que hay guardados en base de datos.
     *
     * @param type
     * @return Long
     */
    int countByType(Boolean type);

    /**
     * Método para validar que la secuencia de DNA no tenga errores (bases nitrogenadas o tamaño de secuencia diferente)
     *
     * @param dna
     * @return boolean
     */
    boolean validateDnaBases(String[] dna);

    /**
     * Método que verifica si una secuencia de DNA es de un Mutante.
     *
     * @param dna
     * @return Dna
     */
    Dna verifyMutantDna(String[] dna);

    StatsDTO getStats();

}
