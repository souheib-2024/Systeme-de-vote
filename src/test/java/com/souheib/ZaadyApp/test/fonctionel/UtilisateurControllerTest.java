package com.souheib.ZaadyApp.test.fonctionel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.souheib.ZaadyApp.controller.UtilisateurControleur;
import com.souheib.ZaadyApp.dto.UtilisateurDTO;
import com.souheib.ZaadyApp.exception.UtilisateurNotFoundException;
import com.souheib.ZaadyApp.model.Role;
import com.souheib.ZaadyApp.service.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class UtilisateurControllerTest {

    private MockMvc mockMvc;

    private UtilisateurService utilisateurService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        utilisateurService = Mockito.mock(UtilisateurService.class);
        UtilisateurControleur controleur = new UtilisateurControleur(utilisateurService);
        mockMvc = MockMvcBuilders.standaloneSetup(controleur).build();
    }

    @Test
    void authentifierUtilisateur_Succes() throws Exception {
        // Arrange
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO(null,"ismael ahmed", "souheib", Role.ADMIN);
        when(utilisateurService.authentifierUtilisateur("souheib", "password"))
                .thenReturn(utilisateurDTO);

        System.out.println("✅ TEST API - AUTHENTIFICATION SUCCÈS");

        // Act & Assert
        mockMvc.perform(post("/utilisateurs/authentifier")
                        .param("prenom", "souheib")
                        .param("motDePasse", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.prenom").value("souheib"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void authentifierUtilisateur_Echec_UtilisateurIntrouvable() throws Exception {
        // Arrange
        when(utilisateurService.authentifierUtilisateur("inconnu", "password"))
                .thenThrow(new UtilisateurNotFoundException("Utilisateur introuvable"));

        System.out.println("❌ TEST API - AUTHENTIFICATION ÉCHEC");

        // Act & Assert
        mockMvc.perform(post("/utilisateurs/authentifier")
                        .param("prenom", "inconnu")
                        .param("motDePasse", "password"))
                .andExpect(status().isUnauthorized());
    }
}
