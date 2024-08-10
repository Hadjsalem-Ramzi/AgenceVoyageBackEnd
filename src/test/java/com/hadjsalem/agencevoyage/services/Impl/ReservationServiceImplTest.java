package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import com.hadjsalem.agencevoyage.entities.Reservation;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.ReservationMapper;
import com.hadjsalem.agencevoyage.repositories.ReservationRepository;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
    @Mock
    private ReservationMapper reservationMapper;
    
    @Mock
    private ReservationRepository reservationRepository;
    
    @InjectMocks
    private ReservationServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<Reservation>  reservationValidators;

    @Test
    void shouldSaveNewReservation(){
        ReservationDto reservationDto = ReservationDto.builder().dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        Reservation reservation= Reservation.builder().dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        Reservation savedReservation= Reservation.builder().id(1L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto expectedReservation= ReservationDto.builder().id(1L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationMapper.fromReservationDto(reservationDto)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(savedReservation);
        when(reservationMapper.fromReservation(savedReservation)).thenReturn(expectedReservation);
        ReservationDto result = underTest.saveReservation(reservationDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedReservation);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenReservationExists() {
        ReservationDto reservationDto = ReservationDto.builder()
                .dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        Reservation reservation = Reservation.builder()
                .dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationMapper.fromReservationDto(reservationDto)).thenReturn(reservation);
        when(reservationRepository.existsByDateReservation(reservationDto.getDateReservation())).thenReturn(true);
        Mockito.doNothing().when(reservationValidators).validate(Mockito.any(Reservation.class));

        assertThatThrownBy(() -> underTest.saveReservation(reservationDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un Reservation est existe avec ce nom");

        verify(reservationRepository, times(1)).existsByDateReservation(reservationDto.getDateReservation());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
    @Test
    void ShouldGetAllReservations() {
        Reservation Reservation1 = new Reservation();
        Reservation1.setId(1L);
        Reservation Reservation2 = new Reservation();
        Reservation2.setId(2L);
        List<Reservation> Reservations = Arrays.asList(Reservation1, Reservation2);
        Page<Reservation> pageReservations = new PageImpl<>(Reservations);

        ReservationDto ReservationDto1 = new ReservationDto();
        ReservationDto1.setId(1L);
        ReservationDto ReservationDto2 = new ReservationDto();
        ReservationDto2.setId(2L);

        when(reservationRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageReservations);
        when(reservationMapper.fromReservation(Reservation1)).thenReturn(ReservationDto1);
        when(reservationMapper.fromReservation(Reservation2)).thenReturn(ReservationDto2);

        // When
        PageResponse<ReservationDto> result = underTest.getReservations(0, 2);

        // Then
        assertEquals(2, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getSize());
        assertEquals(true, result.isFirst());
        assertEquals(true, result.isLast());
    }
    /****         Test GetById Method                */


    @Test
    void ShouldFindReservationById() {
        Long givenReservationId = 1L;
        Reservation reservation = Reservation.builder().id(1L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto expected = ReservationDto.builder().id(1L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        when(reservationRepository.findById(givenReservationId)).thenReturn(Optional.of(reservation));
        when(reservationMapper.fromReservation(reservation)).thenReturn(expected);
        ReservationDto result = underTest.findReservationById(givenReservationId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindReservationById() {
        Long givenReservationById = 5l;
        when(reservationRepository.findById(givenReservationById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findReservationById(givenReservationById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Reservation Not Found");

    }

    /*******                Test Method FindReservationByNumTel                    ********* */


    @Test
    void ShouldfindReservationByDateReservation() {
        LocalDate givenDateReservation = LocalDate.of(2024, Month.SEPTEMBER,10);
        Reservation reservation = Reservation.builder().dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto expected = ReservationDto.builder().dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationRepository.findReservationByDateReservation(givenDateReservation)).thenReturn(Optional.of(reservation));
        when(reservationMapper.fromReservation(reservation)).thenReturn(expected);

        ReservationDto result = underTest.findReservationByDateReservation(givenDateReservation);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindReservationByDateReservation() {
      LocalDate  givenDateReservation= LocalDate.of(2025,Month.OCTOBER,12);

        when(reservationRepository.findReservationByDateReservation(givenDateReservation)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findReservationByDateReservation(givenDateReservation)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updateReservation ****/



    @Test
    void ShouldUpdateReservation() {
        Long givenReservationId = 2L;
        Reservation reservation = Reservation.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto reservationDto = ReservationDto.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        Reservation updatedReservation = Reservation.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto expected = ReservationDto.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationRepository.findById(givenReservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.saveAndFlush(reservation)).thenReturn(updatedReservation);
        when(reservationMapper.fromReservation(updatedReservation)).thenReturn(reservationDto);


        ReservationDto result = underTest.updateReservation(reservationDto, givenReservationId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateReservation() {
        Long givenReservationId = 2L;
        Reservation reservation = Reservation.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto reservationDto = ReservationDto.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        Reservation updatedReservation = Reservation.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto expected = ReservationDto.builder().id(2L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationRepository.findById(givenReservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.saveAndFlush(reservation)).thenReturn(updatedReservation);
        when(reservationMapper.fromReservation(updatedReservation)).thenReturn(reservationDto);

        ReservationDto result = underTest.updateReservation(reservationDto, givenReservationId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenReservationNotFound() {
        Long givenReservationId = 2L;
        ReservationDto reservationDto = ReservationDto.builder()
                .id(2L)
                .dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationRepository.findById(givenReservationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateReservation(reservationDto, givenReservationId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenReservationId = 2L;
        Reservation reservation = Reservation.builder()
                .id(2L)
                .dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto reservationDto = ReservationDto.builder()
                .id(2L)
                .dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationRepository.findById(givenReservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.saveAndFlush(reservation)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateReservation(reservationDto, givenReservationId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenReservationDtoIsInvalid() {
        Long givenReservationId = 2L;
        Reservation reservation = Reservation.builder()
                .id(2L)
                .dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto invalidReservationDto = ReservationDto.builder()
                .id(2L)
                .dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();

        when(reservationRepository.findById(givenReservationId)).thenReturn(Optional.of(reservation));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(reservationValidators).validate(any(Reservation.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateReservation(invalidReservationDto, givenReservationId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteReservationById() {
        Long ReservationId = 1L;
        Reservation reservation = Reservation.builder().id(1L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        ReservationDto reservationDto = ReservationDto.builder().id(1L).dateReservation(LocalDate.of(2024,8,20)).HeureReservation(LocalTime.of(17,30,00)).dureSejour(Period.ofDays(10)).build();
        when(reservationRepository.findById(reservationDto.getId())).thenReturn(Optional.of(reservation));
        underTest.deleteReservation(ReservationId);

        verify(reservationRepository,times(1)).findById(ReservationId);
        verify(reservationRepository,times(1)).deleteById(ReservationId);

    }

    @Test
    void shouldNotDeleteReservationIfNotFound() {
        Long ReservationId = 1L;

        when(reservationRepository.findById(ReservationId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteReservation(ReservationId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Reservation with this id Not Found");
    }

}
