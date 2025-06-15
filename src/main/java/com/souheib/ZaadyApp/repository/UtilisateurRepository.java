package com.souheib.ZaadyApp.repository;

import com.souheib.ZaadyApp.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {


    Optional<Utilisateur> findByPrenom(@Param("prenom") String prenom);
}
