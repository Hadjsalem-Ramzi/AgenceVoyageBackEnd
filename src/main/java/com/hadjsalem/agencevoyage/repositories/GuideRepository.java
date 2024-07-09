package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide,Long> {
    Guide findGuideByNom(String Nom);
}
