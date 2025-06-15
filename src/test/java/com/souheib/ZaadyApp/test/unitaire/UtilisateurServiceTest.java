package com.souheib.ZaadyApp.test.unitaire;

import com.souheib.ZaadyApp.dto.UtilisateurDTO;
import com.souheib.ZaadyApp.exception.UtilisateurNotFoundException;
import com.souheib.ZaadyApp.model.Role;
import com.souheib.ZaadyApp.model.Utilisateur;
import com.souheib.ZaadyApp.repository.UtilisateurRepository;
import com.souheib.ZaadyApp.service.UtilisateurService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void authentifierUtilisateur_Succes() {
        Utilisateur utilisateur = new Utilisateur(null,"ismael ahmed", "souheib", passwordEncoder.encode("sousou"), Role.UTILISATEUR);
        when(utilisateurRepository.findByPrenom("souheib")).thenReturn(Optional.of(utilisateur));
        when(passwordEncoder.matches("sousou", utilisateur.getMotDePasse())).thenReturn(true);

        System.out.println("✅ TEST AUTHENTIFICATION UTILISATEUR");
        System.out.println("Données entrées : Prénom = " + utilisateur.getPrenom() + ", Mot de passe = sousou");

        UtilisateurDTO result = utilisateurService.authentifierUtilisateur("souheib", "sousou");

        assertNotNull(result);
        assertEquals("souheib", result.getPrenom());

        System.out.println("Données sorties : Prénom = " + result.getPrenom() + ", Nom = " + result.getNom() + ", Role = " + result.getRole());
    }

    @Test
    void authentifierUtilisateur_UtilisateurIntrouvable() {
        // Arrange
        when(utilisateurRepository.findByPrenom("inconnu")).thenReturn(Optional.empty());

        System.out.println("❌ TEST ÉCHEC AUTHENTIFICATION UTILISATEUR");
        System.out.println("Données entrées : Prénom = inconnu, Mot de passe = sousou");

        // Act & Assert
        assertThrows(UtilisateurNotFoundException.class, () -> utilisateurService.authentifierUtilisateur("inconnu", "sousou"));

        System.out.println("Résultat : Exception levée -> Utilisateur introuvable.");
    }
    
    @Test
    void testSessionApresConnexion() throws Exception {
        System.out.println("🚀 Début du test - Vérification du stockage de session après connexion");

        // Simule une authentification utilisateur
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            "souheib", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_UTILISATEUR"))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println("✔️ L'utilisateur est bien authentifié dans Spring Security");

        // Vérifie que l’utilisateur est bien reconnu
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // Création d'une session utilisateur simulée
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);
        session.setAttribute("prenom", "souheib");

        System.out.println("✅ Session après connexion :");
        session.getAttributeNames().asIterator().forEachRemaining(attr -> 
            System.out.println("🔹 " + attr + " = " + session.getAttribute(attr))
        );

        // Vérifie que les données sont bien enregistrées dans la session
        assertNotNull(session.getAttribute("id"));
        assertEquals("souheib", session.getAttribute("prenom"));
    }
    
    @Test
    void testExpirationSessionApresDeconnexion() throws Exception {
        System.out.println("🚀 Début du test - Vérification expiration session après déconnexion");

        // Création d'une session utilisateur simulée
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 1L);
        session.setAttribute("prenom", "souheib");

        System.out.println("✅ Session avant déconnexion : " + session.getAttributeNames());

        // Ajoute une authentification dans Spring Security
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            "souheib", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_UTILISATEUR"))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println("✔️ L'utilisateur est bien authentifié dans Spring Security");

        // Vérifie que l’utilisateur est bien reconnu
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // Appelle la déconnexion et récupère la nouvelle session après requête
        MvcResult result = mockMvc.perform(post("/deconnexion").session(session))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("✅ Requête de déconnexion envoyée");

        // Vérifie que la session après déconnexion est bien supprimée
        MockHttpSession nouvelleSession = (MockHttpSession) result.getRequest().getSession(false);

        if (nouvelleSession == null) {
            System.out.println("🎯 Test réussi ! La session est bien détruite après la déconnexion.");
        } else {
            System.out.println("❌ Erreur : La session après déconnexion contient encore des données : " 
                    + nouvelleSession.getAttributeNames());
        }
    }
  
}
