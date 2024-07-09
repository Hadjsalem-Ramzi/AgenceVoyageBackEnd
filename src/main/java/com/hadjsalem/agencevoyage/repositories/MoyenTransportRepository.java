package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoyenTransportRepository extends JpaRepository<MoyenTransport,Long> {
    MoyenTransport findMoyenTransportByNom(String Nom);
}
