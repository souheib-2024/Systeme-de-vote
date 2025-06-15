package com.souheib.ZaadyApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class RedirectionControleur {

 

    @GetMapping({"/","/login"})
    public String login() {
        return "login"; // Redirection vers la page de connexion
    }

    @GetMapping("/accueil")
    public String afficherAccueil(HttpSession session, Model model) {
        String prenom = (String) session.getAttribute("prenom");
        Object roleObj = session.getAttribute("role");
        String role = roleObj != null ? roleObj.toString() : null;

        if (prenom == null) {
            return "redirect:/login";  // redirection vers login
        }

        model.addAttribute("prenom", prenom);
        model.addAttribute("role", role);
        return "accueil";  // affiche la vue accueil.html
    }
    @GetMapping("/choixVote")
    public String afficherChoixVote(HttpSession session, Model model) {
        Long idEvenement = (Long) session.getAttribute("idEvenement");

        model.addAttribute("idEvenement", idEvenement);
        return "choixVote";  // affiche la vue accueil.html
    }
    @PostMapping("/deconnexion")
    public ResponseEntity<Void> deconnecterUtilisateur(HttpSession session) {
        // 🔥 Supprime l'authentification dans Spring Security
        SecurityContextHolder.clearContext();

        // 🔥 Invalide la session actuelle
        session.invalidate();

        return ResponseEntity.ok().build();  // ✅ Retourne 200 OK
    }


}
