package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.TransportCommun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportCommunRepository extends JpaRepository<TransportCommun,Long> {
    TransportCommun findTransportCommunByNom(String Nom);
}
