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

import com.souheib.ZaadyApp.exception.EvenementNotFoundException;
import com.souheib.ZaadyApp.model.Evenement;
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

   /*

    // 🔹 Test : création d'un événement
    @Test
    public void testCreateEvenement() {
        Evenement evenement = new Evenement("Hackathon", "Un hackathon de 24h", LocalDate.now(), LocalDate.now().plusDays(1), new Utilisateur());
        when(evenementRepository.save(evenement)).thenReturn(evenement);

        Evenement result = evenementService.creerEvenement(evenement);

        assertNotNull(result);
        assertEquals("Hackathon", result.getNom());
    }

    @Test
    public void testUpdateEvenement() {
        Evenement evenement = new Evenement("Ancien Nom", "Ancienne description", LocalDate.now(), LocalDate.now().plusDays(1), new Utilisateur());

        when(evenementRepository.findById(1)).thenReturn(Optional.of(evenement));

        Evenement updatedEvenement = new Evenement("Nouveau Nom", "Nouvelle description", LocalDate.now(), LocalDate.now().plusDays(2), new Utilisateur());
        when(evenementRepository.save(any(Evenement.class))).thenReturn(updatedEvenement);

        Evenement result = evenementService.modifierEvenement(1, updatedEvenement);

        assertNotNull(result); // Vérifie que result n'est pas null avant d'accéder à ses propriétés
        assertEquals("Nouveau Nom", result.getNom());
        assertEquals("Nouvelle description", result.getDescription());
    }

*/
  
}
