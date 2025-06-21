package com.souheib.ZaadyApp.test.fonctionel;

import com.souheib.ZaadyApp.model.Evenement;
import com.souheib.ZaadyApp.repository.EvenementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EvenementFonctionnelTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EvenementRepository evenementRepository;

    private Integer idEvenement;

    @BeforeEach
    void setUp() {
        // Création d’un événement en base avant chaque test
        Evenement evenement = new Evenement();
        evenement.setNom("Événement Test");
        evenement.setDescription("Événement à modifier");
        evenement.setDateDebut(LocalDate.now().plusDays(1));
        evenement.setDateFin(LocalDate.now().plusDays(2));
        evenement.setCreateur(1L); // simulateur ID utilisateur

        Evenement saved = evenementRepository.save(evenement);
        idEvenement = saved.getIdEvenement();
    }

    @Test
    @DisplayName("🔐 UTILISATEUR bloqué sur PUT /modifierEvenement/{id}")
    @WithMockUser(username = "souheib", authorities = {"ROLE_UTILISATEUR"})
    void testUtilisateurBloqueSurModification() throws Exception {
    	String payload = """
    			{
    			    "nom": "Nouvel événement admin",
    			    "description": "Mise à jour via PUT",
    			    "dateDebut": "2025-06-25",
    			    "dateFin": "2025-06-26"
    			}
    			""";

        mockMvc.perform(put("/evenements/modifierEvenement/" + idEvenement)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("✅ ADMIN peut modifier un événement")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void testAdminPeutModifierEvenement() throws Exception {
    	String payload = """
    			{
    			    "nom": "Nouvel événement admin",
    			    "description": "Mise à jour via PUT",
    			    "dateDebut": "2025-06-25",
    			    "dateFin": "2025-06-26"
    			}
    			""";

        mockMvc.perform(put("/evenements/modifierEvenement/" + idEvenement)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk());
    }
}