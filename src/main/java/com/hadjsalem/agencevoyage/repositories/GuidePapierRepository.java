package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePapier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuidePapierRepository extends JpaRepository<GuidePapier,Long> {
    Optional<GuidePapier> findGuidePapierByLibelle(String Libelle);

    boolean existsByLibelle(String libelle);

}
