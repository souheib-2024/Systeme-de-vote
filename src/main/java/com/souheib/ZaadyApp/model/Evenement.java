package com.souheib.ZaadyApp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvenement;

    private String nom;
    private String description;
    

    @Column(name = "date_debut", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;

    private Long createur;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChoixVote> choixVotes = new ArrayList<>();

    // Constructeurs
    public Evenement() {}

    public Evenement(String nom, String description, LocalDate dateDebut, LocalDate dateFin, Long createur) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.createur = createur;
    }

    // Getters et Setters
    public Integer getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(Integer idEvenement) {
        this.idEvenement = idEvenement;
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

    public Long getCreateur() {
        return createur;
    }

    public void setCreateur(Long createur) {
        this.createur = createur;
    }

    public List<ChoixVote> getChoixVotes() {
        return choixVotes;
    }

    public void setChoixVotes(List<ChoixVote> choixVotes) {
        this.choixVotes = choixVotes;
    }
}
