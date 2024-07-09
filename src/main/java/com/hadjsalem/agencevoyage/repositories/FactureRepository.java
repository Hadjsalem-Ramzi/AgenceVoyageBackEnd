package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture,Long> {
    Facture findFactureByDesignation(String designation);
}
