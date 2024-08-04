package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Chauffeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChauffeurRepository extends JpaRepository<Chauffeur,Long> {
    Optional<Chauffeur> findChauffeurByNumTelephone(Integer NumTel);
     boolean existsByNumTelephone(Integer numTel);
    Page<Chauffeur> findAll(Pageable pageable);

}
