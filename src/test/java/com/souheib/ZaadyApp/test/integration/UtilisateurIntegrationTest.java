package com.souheib.ZaadyApp.test.integration;

import com.souheib.ZaadyApp.model.Role;
import com.souheib.ZaadyApp.model.Utilisateur;
import com.souheib.ZaadyApp.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UtilisateurIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void testAuthentificationUtilisateur_Succes() throws Exception {
        // Création d’un utilisateur en base
        Utilisateur utilisateur = new Utilisateur(
                null,
                "Ismael Ahmed",
                "souheib",
                passwordEncoder.encode("sousou"),
                Role.ADMIN
        );
        utilisateurRepository.save(utilisateur);

        System.out.println("✅ TEST D’INTÉGRATION - AUTHENTIFICATION UTILISATEUR");

        // Envoi de la requête d'authentification
        MvcResult result = mockMvc.perform(post("/utilisateurs/authentifier")
                        .param("prenom", "souheib")
                        .param("motDePasse", "sousou"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.prenom").value("souheib"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andReturn();

        // Vérification de la session après connexion
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession(false);
        assertNotNull(session, "La session ne doit pas être nulle.");
        assertNotNull(session.getAttribute("SPRING_SECURITY_CONTEXT"), "Le SecurityContext devrait être présent.");

        System.out.println("✅ Session après connexion :");
        session.getAttributeNames().asIterator().forEachRemaining(attr -> 
            System.out.println("🔹 " + attr + " = " + session.getAttribute(attr))
        );

        // Accès à une ressource protégée avec session
        mockMvc.perform(get("/accueil").session(session))
                .andExpect(status().isOk());
    }

 
    @Test
    void testDeconnexionUtilisateur_Succes() throws Exception {
        // Création et connexion de l’utilisateur
        Utilisateur utilisateur = new Utilisateur(
                null,
                "Ismael Ahmed",
                "souheib",
                passwordEncoder.encode("sousou"),
                Role.ADMIN
        );
        utilisateurRepository.save(utilisateur);

        MvcResult login = mockMvc.perform(post("/utilisateurs/authentifier")
                        .param("prenom", "souheib")
                        .param("motDePasse", "sousou"))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpSession session = (MockHttpSession) login.getRequest().getSession(false);
        assertNotNull(session, "La session ne doit pas être nulle après connexion.");

        System.out.println("✅ TEST D’INTÉGRATION - DÉCONNEXION UTILISATEUR");

        // Envoi de la requête de déconnexion
        mockMvc.perform(post("/deconnexion").session(session))
                .andExpect(status().isOk());

        // Vérification que la session est bien supprimée
        MockHttpSession nouvelleSession = (MockHttpSession) login.getRequest().getSession(false);
        assertNull(nouvelleSession, "La session ne doit plus exister après déconnexion.");

        System.out.println("🎯 Test réussi ! La session est bien détruite après déconnexion.");
    }
}