package com.souheib.ZaadyApp.controller;

import com.souheib.ZaadyApp.dto.UtilisateurDTO;
import com.souheib.ZaadyApp.exception.UtilisateurNotFoundException;
import com.souheib.ZaadyApp.service.UtilisateurService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurControleur {

    private final UtilisateurService utilisateurService;

    public UtilisateurControleur(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    
    @PostMapping("/authentifier")
    public ResponseEntity<UtilisateurDTO> authentifierUtilisateur(
            @RequestParam String prenom,
            @RequestParam String motDePasse,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            UtilisateurDTO utilisateur = utilisateurService.authentifierUtilisateur(prenom, motDePasse);

            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    utilisateur.getPrenom(),
                    null,
                    java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()))
                );

            // Place l'authentification dans le contexte Spring Security
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Sauvegarde du contexte dans la session via le repository officiel
            HttpSessionSecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

            // Ajout des infos utilisateur dans la session si besoin
            session.setAttribute("prenom", utilisateur.getPrenom());
            session.setAttribute("role", utilisateur.getRole());
            session.setAttribute("id", utilisateur.getId());
            return ResponseEntity.ok(utilisateur);

        } catch (UtilisateurNotFoundException e) {
            return ResponseEntity.status(401)
                    .header("X-Error-Type", e.getMessage()) // 🔥 Renvoyer le type d'erreur
                    .build();
        }
    }

}
