package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Optional<Reservation> findReservationByDateReservation(LocalDate date);

    boolean existsByDateReservation(LocalDate date);

    Page<Reservation> findAll(Pageable pageable);
}
