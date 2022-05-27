package com.meli.challenge.dna.controller;

import com.meli.challenge.dna.dto.DnaDTO;
import com.meli.challenge.dna.dto.StatsDTO;
import com.meli.challenge.dna.model.Dna;
import com.meli.challenge.dna.service.DnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/dna")
public class DnaRestController {
    @Autowired
    private DnaService dnaService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Dna>> getAllDnas() {
        List<Dna> dnaList = dnaService.findAll();
        if(dnaList!=null && dnaList.size()>0){
            return ResponseEntity.ok(dnaList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="/mutant/")
    public ResponseEntity<?> verifyMutantDna(@RequestBody DnaDTO payload) {
        if(dnaService.validateDnaBases(payload.getDna())){
            if(dnaService.saveOrUpdateDna(dnaService.verifyMutantDna(payload.getDna())).getType()){
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(403).build();
            }
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/stats")
    public ResponseEntity<StatsDTO> getStats(){
        return ResponseEntity.ok(dnaService.getStats());
    }

}
