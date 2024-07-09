package com.hadjsalem.agencevoyage.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate dateReservation;
    private LocalTime HeureReservation;
    private Long dureSejour;

    @ManyToOne
    private Client client;
}
