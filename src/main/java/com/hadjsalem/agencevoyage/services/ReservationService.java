package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.dtos.ReservationDto;

import java.time.LocalDate;

public interface ReservationService {
    ReservationDto findReservationById(Long id);
    ReservationDto findReservationByDateReservation(LocalDate date);
    ReservationDto saveReservation(ReservationDto reservation);
    ReservationDto updateReservation(ReservationDto reservation,Long id);
    void deleteReservation(Long id);

}