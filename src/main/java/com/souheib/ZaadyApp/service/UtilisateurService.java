package com.souheib.ZaadyApp.service;

import com.souheib.ZaadyApp.dto.UtilisateurDTO;
import com.souheib.ZaadyApp.exception.UtilisateurNotFoundException;
import com.souheib.ZaadyApp.model.Utilisateur;
import com.souheib.ZaadyApp.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, BCryptPasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UtilisateurDTO authentifierUtilisateur(String prenom, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByPrenom(prenom)
                .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            throw new UtilisateurNotFoundException("Mot de passe incorrect");
        }

        return new UtilisateurDTO(utilisateur.getId(),utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getRole());
    }


  
}
