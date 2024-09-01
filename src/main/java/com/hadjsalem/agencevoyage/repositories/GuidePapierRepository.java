package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePapier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuidePapierRepository extends JpaRepository<GuidePapier,Long> {
    Optional<GuidePapier> findGuidePapierByName(String Libelle);

    boolean existsByName(String libelle);

    Page<GuidePapier> findAll(Pageable pageable);

}
