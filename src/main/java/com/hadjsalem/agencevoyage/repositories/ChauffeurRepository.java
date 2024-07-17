package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChauffeurRepository extends JpaRepository<Chauffeur,Long> {
    Optional<Chauffeur> findChauffeurByNumTelephone(Long NumTel);
}
