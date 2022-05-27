package com.meli.challenge.dna.dto;

public class DnaDTO {
    String[] dna;

    public DnaDTO() {
    }

    public DnaDTO(String[] dna) {
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
