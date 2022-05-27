package com.meli.challenge.dna.service;

import com.meli.challenge.dna.dto.StatsDTO;
import com.meli.challenge.dna.model.Dna;
import com.meli.challenge.dna.repository.DnaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DnaServiceImplTest {

    @Mock
    private DnaRepository dnaRepository;

    @InjectMocks
    private DnaService dnaService = new DnaServiceImpl();

    private Dna humanDna;
    private Dna mutantDna;
    private Dna badDna;
    private Dna badDnaSize;
    private Dna dummyDna;
    private StatsDTO stats;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyDna = new Dna();
        mutantDna = new Dna(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"},true);
        humanDna = new Dna(new String[]{"ATGCGA","CAGTGC","TTCTGT","AGAAGG","CGCCTA","TCACTG"},false);
        badDna = new Dna(new String[]{"ATGCxGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"},true);
        badDnaSize = new Dna(new String[]{"ATGCCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"},true);
        stats = new StatsDTO(10,5,2);
        badDna.setId("testId");
        System.out.println(badDnaSize.toString());
    }

    @Test
    void findAll() {
        when(dnaRepository.findAll()).thenReturn(List.of(humanDna));
        assertNotNull(dnaService.findAll());
    }

    @Test
    void existsByDna() {
        when(dnaRepository.existsByDna(humanDna.getDna())).thenReturn(true);
        assertTrue(dnaService.existsByDna(humanDna.getDna()));
        assertFalse(dnaService.existsByDna(dummyDna.getDna()));
    }

    @Test
    void saveOrUpdateDna() {
        when(dnaRepository.save(humanDna)).thenReturn(humanDna);
        assertEquals(humanDna,dnaService.saveOrUpdateDna(humanDna));
        when(dnaRepository.existsByDna(mutantDna.getDna())).thenReturn(true);
        assertEquals(mutantDna,dnaService.saveOrUpdateDna(mutantDna));
        assertNull(mutantDna.getId());
    }

    @Test
    void countByType() {
        when(dnaRepository.countByType(true)).thenReturn(10);
        when(dnaRepository.countByType(false)).thenReturn(5);
        assertEquals(10,dnaService.countByType(true));
        assertEquals(5,dnaService.countByType(false));
    }

    @Test
    void validateDnaBases() {
        assertTrue(dnaService.validateDnaBases(humanDna.getDna()));
        assertFalse(dnaService.validateDnaBases(badDna.getDna()));
        assertFalse(dnaService.validateDnaBases(badDnaSize.getDna()));
    }

    @Test
    void verifyMutantDna() {
        assertTrue(dnaService.verifyMutantDna(mutantDna.getDna()).getType());
        assertFalse(dnaService.verifyMutantDna(humanDna.getDna()).getType());
        mutantDna.setDna(new String[] {"ATGCGAATGCGA","CATTGCATGCGA","TTATGTATGCGA","AGAATGATGCGA","CCCCTAATGCGA","TCACTGATGCGA","ATGCGAATGCGA","CATTGCATGCGA","TTATGTATGCGA","AGTATGATGGGA","CCCTTAATGCGA","TCACTGAGGCGA"});
        assertTrue(dnaService.verifyMutantDna(mutantDna.getDna()).getType());
    }

    @Test
    void getStats() {
        when(dnaRepository.countByType(true)).thenReturn(stats.getCount_mutant_dna());
        when(dnaRepository.countByType(false)).thenReturn(stats.getCount_human_dna());
        assertEquals(stats.getRatio(), dnaService.getStats().getRatio());
        stats.setRatio(1);
        stats.setCount_human_dna(10);
        stats.setCount_mutant_dna(10);
        assertNotEquals(stats.getRatio(),dnaService.getStats().getRatio());
    }
}