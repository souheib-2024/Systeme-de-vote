package com.souheib.ZaadyApp.dto;

import com.souheib.ZaadyApp.model.Role;

public class UtilisateurDTO {
	private Long id;
    private String nom;
    private String prenom;
    private Role role;

    public UtilisateurDTO(Long id,String nom, String prenom, Role role) {
    	this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }
    public Long getId() {return id;}
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public Role getRole() { return role; }
}
