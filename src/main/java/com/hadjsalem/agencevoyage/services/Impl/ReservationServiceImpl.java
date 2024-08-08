package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import com.hadjsalem.agencevoyage.entities.Reservation;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.ReservationMapper;
import com.hadjsalem.agencevoyage.repositories.ReservationRepository;
import com.hadjsalem.agencevoyage.services.ReservationService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationMapper mapper;
    private ObjectsValidators<Reservation> reservationValidators;

    @Override
    public ReservationDto findReservationById(Long id) {
    Optional<Reservation> optionalReservation =reservationRepository.findById(id);
    return optionalReservation.map(mapper::fromReservation).orElseThrow(()->new NoSuchElementException("Reservation Not Found"));

    }

    @Override
    public ReservationDto findReservationByDateReservation(LocalDate date) {
        Optional<Reservation> reservation= reservationRepository.findReservationByDateReservation(date);
        if (!reservation.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromReservation(reservation.get());
    }
    @Override
    public ReservationDto saveReservation(ReservationDto reservationDto) {
      Reservation reservation = mapper.fromReservationDto(reservationDto);
      if(reservation == null){
          throw new IllegalArgumentException("reservation est null");
      }
      reservationValidators.validate(reservation);
      boolean exists = reservationRepository.existsByDateReservation(reservationDto.getDateReservation());
      if(exists){
          throw  new DuplicateEntryException("un reservation est existe avec cette Date de reservation");
      }
      Reservation savedReservation = reservationRepository.save(reservation);
      return  mapper.fromReservation(savedReservation);
    }

    @Override
    public ReservationDto updateReservation(ReservationDto reservationdto, Long id) {
        Optional<Reservation> reservation1 = reservationRepository.findById(id);
        if (reservation1.isPresent()) {
            Reservation reservation2 = reservation1.get();
            reservation2.setId(id);
            reservationValidators.validate(reservation2);
            if (reservationRepository.existsByDateReservation(reservation2.getDateReservation())) {
                throw new DuplicateEntryException("un reservation est existe avec cette date ");
            }
            Reservation reservation3 = reservationRepository.saveAndFlush(reservation2);
            return mapper.fromReservation(reservation3);
        } else {
            throw new EntityNotFoundException("reservation Not Found");
        }
    }
    @Override
    public void deleteReservation(Long id) {
        if( reservationRepository.findById(id).isPresent()){
            reservationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("reservation with this id Not Found");
        }
    }

    public PageResponse<ReservationDto> getReservations(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Reservation> Reservations = reservationRepository.findAll(pageRequest);
        List<ReservationDto> ReservationList = Reservations.map(mapper::fromReservation).getContent();

        return new PageResponse<>(
                ReservationList,
                Reservations.getNumber(),
                Reservations.getSize(),
                Reservations.getTotalElements(),
                Reservations.getTotalPages(),
                Reservations.isFirst(),
                Reservations.isLast()
        );
    }
}
