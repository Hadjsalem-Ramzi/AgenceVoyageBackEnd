package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture,Long> {
    Optional<Facture> findFactureByDesignation(String designation);
}
