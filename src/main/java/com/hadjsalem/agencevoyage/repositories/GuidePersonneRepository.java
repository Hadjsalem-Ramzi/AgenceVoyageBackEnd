package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuidePersonneRepository extends JpaRepository<GuidePersonne,Long> {
    Optional<GuidePersonne> findGuidePersonneByName(String name);
    boolean existsByName(String name);

    Page<GuidePersonne> findAll(Pageable pageable);
}
