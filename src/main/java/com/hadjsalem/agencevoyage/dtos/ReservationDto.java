package com.hadjsalem.agencevoyage.dtos;


import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(Long id, LocalDate dateReservation, LocalTime HeureReservation, Long dureSejour) {
}
