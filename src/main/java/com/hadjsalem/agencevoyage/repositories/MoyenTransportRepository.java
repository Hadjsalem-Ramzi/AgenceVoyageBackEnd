package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoyenTransportRepository extends JpaRepository<MoyenTransport,Long> {
    Optional<MoyenTransport> findMoyenTransportByName(String name);
    boolean existsByName(String name);
    Page<MoyenTransport> findAll(Pageable pageable);
}
