package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePapier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuidePapierRepository extends JpaRepository<GuidePapier,Long> {
    GuidePapier findGuidePapierByLibelle(String Libelle);
}
