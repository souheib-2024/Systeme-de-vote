package com.souheib.ZaadyApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.souheib.ZaadyApp.dto.EvenementDTO;
import com.souheib.ZaadyApp.dto.UtilisateurDTO;
import com.souheib.ZaadyApp.exception.EvenementNotFoundException;
import com.souheib.ZaadyApp.exception.UtilisateurNotFoundException;
import com.souheib.ZaadyApp.model.Evenement;
import com.souheib.ZaadyApp.service.EvenementService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;

    @GetMapping
    public List<Evenement> obtenirTousLesEvenements() {
        return evenementService.obtenirTousLesEvenements();
    }

    @PutMapping("/{id}")
    public Evenement modifierEvenement(@PathVariable Integer id, @RequestBody Evenement evenement) {
        return evenementService.modifierEvenement(id, evenement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerEvenement(@PathVariable Integer id) {
        evenementService.supprimerEvenement(id);
        return ResponseEntity.ok().build();
    }
    
    
    @PostMapping("/creerEvenement")
    public ResponseEntity<EvenementDTO> creerEvenement(HttpSession session, @RequestBody Evenement evenement) {
        // 🔹 Récupérer l'utilisateur authentifié depuis la session
        Long utilisateurId = (Long) session.getAttribute("id");
        // 🔹 Vérifier si l'utilisateur est bien connecté
        if (utilisateurId == null) {
            throw new UtilisateurNotFoundException("ID introuvable");
        }
        // 🔹 Associer l'ID de l'utilisateur à l'événement sans `setOrganisateur()`
        evenement.setCreateur(utilisateurId);

        // 🔹 Enregistrer l’événement dans la base de données
        EvenementDTO evenementCree = evenementService.creerEvenement(evenement);
        
        // 🔹 Sauvegarder l'ID de l'événement dans la session
        session.setAttribute("idEvenement", evenementCree.getId());
        return ResponseEntity.ok(evenementCree);
    }
    
    
    
    
    
}
