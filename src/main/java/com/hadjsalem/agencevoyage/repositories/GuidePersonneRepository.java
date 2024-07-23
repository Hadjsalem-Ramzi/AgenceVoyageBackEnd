package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuidePersonneRepository extends JpaRepository<GuidePersonne,Long> {
    Optional<GuidePersonne> findGuidePersonneByNumTel(Integer Numtel);
    boolean existsByNumTel(Integer numTel);
}
