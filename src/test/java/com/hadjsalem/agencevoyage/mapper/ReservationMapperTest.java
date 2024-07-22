package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import com.hadjsalem.agencevoyage.entities.Reservation;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationMapperTest {

    @Autowired
    ReservationMapper underTest;

    @Test
    public  void ShouldMapReservationToReservationDto(){
        Reservation givenReservation = Reservation.builder().dateReservation(LocalDate.ofEpochDay(2024-05-03)).HeureReservation(LocalTime.of(12,30,00)).build();
        ReservationDto expected = ReservationDto.builder().dateReservation(LocalDate.ofEpochDay(2024-05-03)).HeureReservation(LocalTime.of(12,30,00)).build();

        ReservationDto result = underTest.fromReservation(givenReservation);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    public  void ShouldMapReservationDtoToReservation(){
        ReservationDto givenReservation = ReservationDto.builder().dateReservation(LocalDate.ofEpochDay(2024-05-03)).HeureReservation(LocalTime.of(12,30,00)).build();
        Reservation expected = Reservation.builder().dateReservation(LocalDate.ofEpochDay(2024-05-03)).HeureReservation(LocalTime.of(12,30,00)).build();

        Reservation result = underTest.fromReservationDto(givenReservation);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldNotMapNullReseravationToreservationDto(){
        Reservation givenReservation = null;
        assertThatThrownBy(()-> underTest.fromReservation(givenReservation)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullReseravationDtoToreservation(){
        ReservationDto givenReservation = null;
        assertThatThrownBy(()-> underTest.fromReservationDto(givenReservation)).isInstanceOf(IllegalArgumentException.class);
    }

}
