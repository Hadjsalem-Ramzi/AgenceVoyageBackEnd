package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompagnieTransportRepository extends JpaRepository<CompagnieTransport,Long> {
    CompagnieTransport findCompagnieTransportByNom(String Nom);
}