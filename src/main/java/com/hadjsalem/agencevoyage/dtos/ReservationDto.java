package com.hadjsalem.agencevoyage.dtos;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservationDto {
private Long id;
private LocalDate dateReservation;
private LocalTime HeureReservation;
private Long dureSejour;
}
