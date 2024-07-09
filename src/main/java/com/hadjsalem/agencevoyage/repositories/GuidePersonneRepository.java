package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuidePersonneRepository extends JpaRepository<GuidePersonne,Long> {
    GuidePersonne findGuidePersonneByNom(String Nom);
}
