package com.hadjsalem.agencevoyage.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotNull(message = "La date de réservation ne peut pas être null.")
    @FutureOrPresent(message = "La date de réservation doit être dans le présent ou le futur.")
    private LocalDate dateReservation;

    @NotNull(message = "L'heure de réservation ne peut pas être null.")
    private LocalTime HeureReservation;

    @NotNull(message = "La durée du séjour ne peut pas être null.")
    @Min(value = 1, message = "La durée du séjour doit être d'au moins 1 jour.")
    private Period dureSejour;

    @ManyToOne
    private Client client;
}
