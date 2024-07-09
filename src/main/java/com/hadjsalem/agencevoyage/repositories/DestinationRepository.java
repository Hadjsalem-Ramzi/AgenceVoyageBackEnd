package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination,Long> {
    Destination findDestinationByVille(String Pays);
}
