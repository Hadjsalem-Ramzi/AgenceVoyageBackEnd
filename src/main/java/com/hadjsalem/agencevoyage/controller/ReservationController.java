package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.ReservationApi;
import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import com.hadjsalem.agencevoyage.services.Impl.ReservationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ReservationController implements ReservationApi {

    private  final ReservationServiceImpl ReservationService ;


    @Override
    public ReservationDto saveReservation(ReservationDto Reservation) {
        return ReservationService.saveReservation(Reservation) ;
    }

    @Override
    public ReservationDto findReservationById(Long id) {
        return ReservationService.findReservationById(id) ;
    }

    @Override
    public ReservationDto findReservationByDateReservation(LocalDate DateReservation) {
        return ReservationService.findReservationByDateReservation(DateReservation);
    }


    @Override
    public ReservationDto updateReservation(ReservationDto Reservation, Long id) {
        return ReservationService.updateReservation(Reservation, id) ;
    }

    @Override
    public void deleteReservation(Long id) {
      ReservationService.deleteReservation(id);
    }

    @Override
    public PageResponse<ReservationDto> getReservations(int page, int size) {
        return ReservationService.getReservations(page, size) ;
    }
}
