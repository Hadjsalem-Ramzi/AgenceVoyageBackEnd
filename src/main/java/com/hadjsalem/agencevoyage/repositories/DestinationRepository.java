package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination,Long> {
    Optional<Destination> findDestinationByVille(String Pays);
}
