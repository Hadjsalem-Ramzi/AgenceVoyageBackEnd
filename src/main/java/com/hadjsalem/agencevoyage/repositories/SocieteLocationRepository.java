package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocieteLocationRepository extends JpaRepository<SocieteLocation,Long> {
SocieteLocation findSocieteLocationByNom(String Nom);
}
