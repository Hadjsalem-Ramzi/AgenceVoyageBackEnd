package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findClientByEmail(String email);
    boolean existsByEmail(String email);

    Page<Client> findAll(Pageable pageable);
}
