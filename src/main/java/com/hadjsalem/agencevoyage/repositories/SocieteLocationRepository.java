package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocieteLocationRepository extends JpaRepository<SocieteLocation,Long> {
Optional<SocieteLocation> findSocieteLocationByNom(String Nom);
}
