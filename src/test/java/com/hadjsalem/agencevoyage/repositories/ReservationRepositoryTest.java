package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Reservation;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void SetUp(){
        reservationRepository.save(Reservation.builder().dateReservation(LocalDate.of(2024,05,3)).HeureReservation(LocalTime.of(8,00,00)).dureSejour(Period.ofDays(10)).build());
    }

    @Test
    public void SouldFindReservationByDateReservation(){
        LocalDate date= LocalDate.of(2024,05,3);

        Optional<Reservation> result = reservationRepository.findReservationByDateReservation(date);

        AssertionsForClassTypes.assertThat(result).isPresent();
    }

    @Test
    public void SouldNotFindReservationByDateReservation(){
        LocalDate date= LocalDate.of(2026,05,3);

        Optional<Reservation> result = reservationRepository.findReservationByDateReservation(date);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }


}
