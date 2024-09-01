package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.TransportCommun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportCommunRepository extends JpaRepository<TransportCommun,Long> {
    Optional<TransportCommun> findTransportCommunByName(String name);
    boolean existsByNumTel(Integer numTel);
    Page<TransportCommun> findAll(Pageable pageable);
}
