package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Hotel findHotelByLibelle(String Libelle);
}
