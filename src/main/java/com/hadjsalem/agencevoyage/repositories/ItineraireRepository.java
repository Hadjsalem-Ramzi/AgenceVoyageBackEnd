package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Itineraire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItineraireRepository extends JpaRepository<Itineraire,Long> {
    Optional<Itineraire> findItineraireByLibelle (String Libelle);
}
