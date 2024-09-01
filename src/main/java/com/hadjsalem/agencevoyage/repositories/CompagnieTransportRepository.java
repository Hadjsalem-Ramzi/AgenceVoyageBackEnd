package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompagnieTransportRepository extends JpaRepository<CompagnieTransport,Long> {
    Optional<CompagnieTransport> findCompagnieTransportByName(String name);
    boolean existsByNumTel(Integer numTel);
    Page<CompagnieTransport> findAll(Pageable pageable);

}
