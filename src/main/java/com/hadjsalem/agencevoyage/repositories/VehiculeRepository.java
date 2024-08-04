package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
    Optional<Vehicule> findVehiculeByImmatricule(String immatricule);
    boolean existsByImmatricule(String immat );

    Page<Vehicule> findAll(Pageable pageable);
}
