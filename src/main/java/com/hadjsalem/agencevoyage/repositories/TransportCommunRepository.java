package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.TransportCommun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportCommunRepository extends JpaRepository<TransportCommun,Long> {
    Optional<TransportCommun> findTransportCommunByNom(String Nom);
    boolean existsByNumTel(Integer numTel);
}
