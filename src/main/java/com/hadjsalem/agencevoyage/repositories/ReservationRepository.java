package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Reservation findReservationByDateReservation(LocalDate date);
}
