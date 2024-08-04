package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocieteLocationRepository extends JpaRepository<SocieteLocation,Long> {
Optional<SocieteLocation> findSocieteLocationByNom(String Nom);
    boolean existsByNumTel(Integer numTel);

    Page <SocieteLocation>findAll(Pageable pageable);
}
