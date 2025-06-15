package com.souheib.ZaadyApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.souheib.ZaadyApp.model.Evenement;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Integer> {

	Optional<Evenement> findByNom(String nom);
}
