package com.souheib.ZaadyApp.service;


import com.souheib.ZaadyApp.dto.EvenementDTO;
import com.souheib.ZaadyApp.exception.EvenementNotFoundException;
import com.souheib.ZaadyApp.model.Evenement;
import com.souheib.ZaadyApp.repository.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    // 🔹 Obtenir tous les événements
    public List<Evenement> obtenirTousLesEvenements() {
        return evenementRepository.findAll();
    }

    // 🔹 Obtenir un événement par ID
    public Evenement obtenirEvenementParNom(String nom) {
        return evenementRepository.findByNom(nom)
                .orElseThrow(() -> new EvenementNotFoundException("Événement non trouvé avec Nom : "));
    }

    // 🔹 Créer un événement
    public EvenementDTO creerEvenement(Evenement evenement) {
        	evenementRepository.save(evenement);
        	return new EvenementDTO(evenement.getIdEvenement(),evenement.getNom());
    }

    // 🔹 Modifier un événement (seulement si existant)
    public Evenement modifierEvenement(Integer id, Evenement evenement) {
        Evenement evenementExistant = evenementRepository.findById(id)
                .orElseThrow(() -> new EvenementNotFoundException("Événement non trouvé avec l'ID : " + id));

        evenementExistant.setNom(evenement.getNom());
        evenementExistant.setDescription(evenement.getDescription());
        evenementExistant.setDateDebut(evenement.getDateDebut());
        evenementExistant.setDateFin(evenement.getDateFin());

        return evenementRepository.save(evenementExistant);
    }

    // 🔹 Supprimer un événement
    public void supprimerEvenement(Integer id) {
        Evenement evenement = evenementRepository.findById(id)
                .orElseThrow(() -> new EvenementNotFoundException("Événement non trouvé avec l'ID : " + id));

        evenementRepository.delete(evenement);
    }
}
