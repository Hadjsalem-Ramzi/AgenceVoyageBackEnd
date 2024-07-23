package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide,Long> {
    Optional<Guide> findGuideByNumTel(Integer numTel);

    boolean existsByNumTel(Integer numTel);
}
