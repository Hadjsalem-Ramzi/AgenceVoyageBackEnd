package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findClientByFirstName(String firstName);
    boolean existsByEmail(String email);
}
