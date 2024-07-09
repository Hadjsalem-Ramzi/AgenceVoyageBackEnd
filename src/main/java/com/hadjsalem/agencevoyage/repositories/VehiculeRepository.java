package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
    Vehicule findVehiculeByImmatricule(String immatricule);
}
