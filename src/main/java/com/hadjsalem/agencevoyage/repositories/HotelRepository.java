package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Optional<Hotel> findHotelByLibelle(String Libelle);
    boolean existsByLibelle(String libelle);
}
