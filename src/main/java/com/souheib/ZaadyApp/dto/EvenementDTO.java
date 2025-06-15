package com.souheib.ZaadyApp.dto;

import java.time.LocalDate;

public class EvenementDTO {

    private Integer id;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Constructeurs
    public EvenementDTO() {}

    public EvenementDTO(Integer id,String nom) {
        this.id = id;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
