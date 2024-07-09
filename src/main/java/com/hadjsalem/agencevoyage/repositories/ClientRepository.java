package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findClientByFirstName(String firstName);
}
