package com.meli.challenge.dna.service;

import com.meli.challenge.dna.dto.StatsDTO;
import com.meli.challenge.dna.model.Dna;
import com.meli.challenge.dna.repository.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DnaServiceImpl implements DnaService {

    @Autowired
    private DnaRepository dnaRepository;

    @Override
    public List<Dna> findAll() {
        return dnaRepository.findAll();
    }

    @Override
    public boolean existsByDna(String[] dna) {
        return dnaRepository.existsByDna(dna);
    }

    @Override
    public Dna saveOrUpdateDna(Dna dna) {
        if(!existsByDna(dna.getDna())){
            return dnaRepository.save(dna);
        }else{
            return dna;
        }
    }

    @Override
    public int countByType(Boolean type) {
        return dnaRepository.countByType(type);
    }

    @Override
    public boolean validateDnaBases(String[] dnaArray) {
        for (int i = 0; i < dnaArray.length - 1; i++) {
            if (!dnaArray[i].matches("^[ATGC]+$")) {
                return false;
            }
            if (dnaArray[i].length() != dnaArray.length) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Dna verifyMutantDna(String[] dna) {
        return new Dna(dna, analyzeDnaArray(dna));
    }

    @Override
    public StatsDTO getStats() {
        int count_mutant_dna = dnaRepository.countByType(true);
        int count_human_dna = dnaRepository.countByType(false);
        float ratio = (float) count_mutant_dna/count_human_dna;
        return new StatsDTO(count_mutant_dna,count_human_dna,ratio);
    }


    /**
     * Método que recorre un array con la información del DNA y evalúa si existe más de una secuencia de 4 bases nitrogenadas iguales (horizonta, vertical y oblicua)
     * Se usan índices diferentes en cada condición para manejar el recorrido sin desbordar el array.
     *
     * @param dnaArray
     * @return
     */
    private boolean analyzeDnaArray(String[] dnaArray) {
        int diagonalMatches = 0;
        int horizontalMatches = 0;
        int verticalMatches = 0;
        int length = dnaArray.length;
        int repeat = 4;

        for (int i = 0, j = 0, k = 0; i < length || j < length || k < length; i++, j++, k++) {
            for (int x = 0, y = 0, z = 0; x < length || y < length || z < length; x++, y++, z++) {
                if (x < length - 3) {
                    if (dnaArray[i].charAt(x) == dnaArray[i].charAt(x + 1)
                            && dnaArray[i].charAt(x) == dnaArray[i].charAt(x + 2)
                            && dnaArray[i].charAt(x) == dnaArray[i].charAt(x + 3)) {
                        horizontalMatches++;
                        x += 3;
                    }
                }
                if (y < length - 3) {
                    if (dnaArray[y].charAt(j) == dnaArray[y + 1].charAt(j)
                            && dnaArray[y].charAt(j) == dnaArray[y + 2].charAt(j)
                            && dnaArray[y].charAt(j) == dnaArray[y + 3].charAt(j)) {
                        verticalMatches++;
                        y += 3;
                    }
                }
                if (k < length - 3 && z < length - 3) {
                    char[] dnaSequence = new char[repeat];
                    char[] dnaSequenceInverse = new char[repeat];
                    for (int counter = 0; counter < repeat; counter++) {
                        dnaSequence[counter] = dnaArray[k + counter].charAt(z + counter);
                        dnaSequenceInverse[counter] = dnaArray[((dnaArray.length - 1) - k) - counter].charAt(z + counter);
                    }
                    if (String.valueOf(dnaSequence).matches("(.)\\1+") || String.valueOf(dnaSequenceInverse).matches("(.)\\1+")) {
                        diagonalMatches++;
                    }
                }
            }
        }
        int repeatSum = diagonalMatches + horizontalMatches + verticalMatches;
        System.out.println(repeatSum);
        return repeatSum > 1;
    }
}
