package com.souheib.ZaadyApp.test.unitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.souheib.ZaadyApp.dto.EvenementDTO;
import com.souheib.ZaadyApp.exception.EvenementNotFoundException;
import com.souheib.ZaadyApp.model.Evenement;
import com.souheib.ZaadyApp.model.Role;
import com.souheib.ZaadyApp.model.Utilisateur;
import com.souheib.ZaadyApp.repository.EvenementRepository;
import com.souheib.ZaadyApp.service.EvenementService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EvenementServiceTest {

    @InjectMocks
    private EvenementService evenementService;

    @Mock
    private EvenementRepository evenementRepository;


    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    
    
    // 🔹 Test : création d'un événement
    @Test
    void creerEvenement_Succes() {
        System.out.println("✅ TEST CRÉATION ÉVÉNEMENT");

        // Simuler l'encodage du mot de passe pour l'utilisateur
        when(passwordEncoder.encode("sousou")).thenReturn("motDePasseEncode");

        Utilisateur utilisateur = new Utilisateur(null, "ismael ahmed", "souheib", "motDePasseEncode", Role.UTILISATEUR);
        System.out.println("Utilisateur simulé : " + utilisateur.getPrenom() + ", role = " + utilisateur.getRole());

        // Création d’un événement associé à l’utilisateur
        Evenement evenement = new Evenement("Hackathon", "Un hackathon de 24h", LocalDate.now(), LocalDate.now().plusDays(1), utilisateur.getId());

        // Simuler la sauvegarde en base
        when(evenementRepository.save(evenement)).thenReturn(evenement);

        // Appel de la méthode testée
        evenementService.creerEvenement(evenement);
        // Affichage du résultat
        System.out.println("📦 Événement créé :");
        System.out.println("Nom = " + evenement.getNom());
        System.out.println("Description = " + evenement.getDescription());
        System.out.println("Date = "+evenement.getDateDebut());

    }

  
}
