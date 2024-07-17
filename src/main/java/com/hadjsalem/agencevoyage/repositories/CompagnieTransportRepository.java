package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompagnieTransportRepository extends JpaRepository<CompagnieTransport,Long> {
    Optional<CompagnieTransport> findCompagnieTransportByNom(String Nom);
}
