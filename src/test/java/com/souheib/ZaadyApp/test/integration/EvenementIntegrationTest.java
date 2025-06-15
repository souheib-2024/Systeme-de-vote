package com.souheib.ZaadyApp.test.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.souheib.ZaadyApp.model.Evenement;
import com.souheib.ZaadyApp.model.Role;
import com.souheib.ZaadyApp.model.Utilisateur;
import com.souheib.ZaadyApp.repository.EvenementRepository;
import com.souheib.ZaadyApp.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class EvenementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Utilisateur adminTest;
    private Utilisateur utilisateurTest;
/*
    @BeforeEach
    public void initialiserDonnees() {
        evenementRepository.deleteAll();
        utilisateurRepository.deleteAll();

        // 🔹 Création et enregistrement d'un ADMIN
        adminTest = new Utilisateur();
        adminTest.setNom("Admin");
        adminTest.setPrenom("Boss");
        adminTest.setMotDePasse("hashedPassword");
        adminTest.setRole(Role.ADMIN); // ✅ Défini via l'énumération
        adminTest = utilisateurRepository.save(adminTest);

        // 🔹 Création et enregistrement d'un UTILISATEUR
        utilisateurTest = new Utilisateur();
        utilisateurTest.setNom("Souheib");
        utilisateurTest.setPrenom("User");
        utilisateurTest.setMotDePasse("hashedPassword");
        utilisateurTest.setRole(Role.UTILISATEUR); // ✅ Défini via l'énumération
        utilisateurTest = utilisateurRepository.save(utilisateurTest);
    }

 // 🔹 **Test : Un ADMIN peut créer un événement**
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void testAdminPeutCreerEvenement() throws Exception {
        Evenement nouvelEvenement = new Evenement("Événement Admin", "Description test", LocalDate.now(), LocalDate.now().plusDays(3), adminTest);

        mockMvc.perform(post("/evenements/creerEvenement") // ✅ Utilise la bonne URL
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nouvelEvenement)))
                .andExpect(status().isOk());
    }

    // 🔹 **Test : Un UTILISATEUR peut créer un événement**
    @Test
    @WithMockUser(username = "utilisateur", authorities = {"ROLE_UTILISATEUR"})
    public void testUtilisateurPeutCreerEvenement() throws Exception {
        Evenement nouvelEvenement = new Evenement("Événement Utilisateur", "Description test", LocalDate.now(), LocalDate.now().plusDays(3), utilisateurTest);

        mockMvc.perform(post("/evenements/creerEvenement") // ✅ Utilise la bonne URL
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nouvelEvenement)))
                .andExpect(status().isOk());
    }
*/
}
