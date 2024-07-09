package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Itineraire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IteneraireRepository extends JpaRepository<Itineraire,Long> {
    Itineraire findItineraireByLibelle (String Libelle);
}
