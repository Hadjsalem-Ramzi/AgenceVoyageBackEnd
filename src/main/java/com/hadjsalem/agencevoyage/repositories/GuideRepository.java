package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Guide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide,Long> {
    Optional<Guide> findGuideByName(String name);

    boolean existsByName(String name);

    Page<Guide> findAll(Pageable pageable);
}
