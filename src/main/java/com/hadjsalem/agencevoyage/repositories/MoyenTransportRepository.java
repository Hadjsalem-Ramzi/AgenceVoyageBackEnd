package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoyenTransportRepository extends JpaRepository<MoyenTransport,Long> {
    Optional<MoyenTransport> findMoyenTransportByNom(String Nom);
    boolean existsByNom(String nom);
}
