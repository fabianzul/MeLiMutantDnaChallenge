package com.meli.challenge.dna.controller;

import com.meli.challenge.dna.dto.StatsDTO;
import com.meli.challenge.dna.model.Dna;
import com.meli.challenge.dna.repository.DnaRepository;
import com.meli.challenge.dna.service.DnaService;
import com.meli.challenge.dna.service.DnaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class DnaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DnaService dnaService;

    private Dna humanDna;
    private Dna mutantDna;
    private StatsDTO stats;

    @BeforeEach
    void setUp() {
        mutantDna = new Dna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"}, true);
        humanDna = new Dna(new String[]{"ATGCGA", "CAGTGC", "TTCTGT", "AGAAGG", "CGCCTA", "TCACTG"}, false);
        stats = new StatsDTO(1,1,1);
    }

    @Test
    void getAllDnas() throws Exception {
        when(dnaService.findAll()).thenReturn(List.of(mutantDna));
        mockMvc.perform(get("/dna/")).andExpect(status().is(200)).andExpect(content().string("[{\"id\":null,\"dna\":[\"ATGCGA\"," +
                "\"CAGTGC\"," +
                "\"TTATGT\"," +
                "\"AGAAGG\"," +
                "\"CCCCTA\"," +
                "\"TCACTG\"]," +
                "\"type\":true}]"));
    }

    @Test
    void verifyMutantDna() throws Exception {
        when(dnaService.verifyMutantDna(any())).thenReturn(mutantDna);
        when(dnaService.validateDnaBases(any())).thenReturn(true);
        when(dnaService.saveOrUpdateDna(any())).thenReturn(mutantDna);
        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}"))
                .andExpect(status().is(200));
    }

    @Test
    void getStats() throws Exception {
        when(dnaService.getStats()).thenReturn(stats);
        mockMvc.perform(get("/dna/stats")).andExpect(status().is(200)).andExpect(content().string("{" +
                "\"count_mutant_dna\":1," +
                "\"count_human_dna\":1," +
                "\"ratio\":1.0" +
                "}"));
    }
}