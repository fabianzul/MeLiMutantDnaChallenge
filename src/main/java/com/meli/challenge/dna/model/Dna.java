package com.meli.challenge.dna.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "dna")
public class Dna {
    @Id
    private String id;
    private String[] dna;
    private Boolean type;

    public Dna() {
    }

    public Dna(String[] dna, Boolean type) {
        this.dna = dna;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Dna{" +
                "id='" + id + '\'' +
                ", dna=" + Arrays.toString(dna) +
                ", type=" + type +
                '}';
    }
}
