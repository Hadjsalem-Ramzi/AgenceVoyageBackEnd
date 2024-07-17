package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import com.hadjsalem.agencevoyage.entities.Reservation;
import com.hadjsalem.agencevoyage.mapper.ReservationMapper;
import com.hadjsalem.agencevoyage.repositories.ReservationRepository;
import com.hadjsalem.agencevoyage.services.ReservationService;
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
    public ReservationDto saveReservation(ReservationDto ReservationDto) {
      Reservation Reservation1 = mapper.fromReservationDto(ReservationDto);
      Reservation Reservation2= reservationRepository.save(Reservation1);
      return mapper.fromReservation(Reservation2);
    }

    @Override
    public ReservationDto updateReservation(ReservationDto Reservationdto, Long id) {
      Optional<Reservation> Reservation1 = reservationRepository.findById(id);
      if(Reservation1.isPresent()){
      Reservation Reservation2 = Reservation1.get();
      Reservation Reservation3= reservationRepository.saveAndFlush(Reservation2);
      return mapper.fromReservation(Reservation3);
      }else {
          throw  new NoSuchElementException("Reservation NotFound");
      }
    }

    @Override
    public void deleteReservation(Long id) {
       reservationRepository.deleteById(id);
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
