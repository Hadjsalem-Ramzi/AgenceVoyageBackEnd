package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChauffeurRepository extends JpaRepository<Chauffeur,Long> {
    Chauffeur findChauffeurByNom(String Nom);
}
