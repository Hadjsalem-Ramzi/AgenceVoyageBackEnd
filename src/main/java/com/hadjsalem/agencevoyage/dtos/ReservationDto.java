package com.hadjsalem.agencevoyage.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public record ReservationDto(Long id,LocalDate dateReservation,LocalTime HeureReservation,Long dureSejour) {
}
