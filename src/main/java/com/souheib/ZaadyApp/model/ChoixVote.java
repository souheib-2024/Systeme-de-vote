package com.souheib.ZaadyApp.model;

import jakarta.persistence.*;

@Entity
public class ChoixVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChoix;

    private String libelle;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "evenement_id", nullable = false)
    private Evenement evenement;

    // Constructeurs
    public ChoixVote() {}

    public ChoixVote(String libelle, String imageUrl, Evenement evenement) {
        this.libelle = libelle;
        this.imageUrl = imageUrl;
        this.evenement = evenement;
    }

    // Getters et Setters
}
