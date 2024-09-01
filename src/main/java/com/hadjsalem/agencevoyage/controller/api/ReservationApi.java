package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface ReservationApi {

    @PostMapping(value = APP_ROOT+"/Reservations/saveReservation",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationDto saveReservation(@RequestBody  ReservationDto Reservation);

    @GetMapping(value = APP_ROOT +"/Reservations/{idReservation}",produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationDto findReservationById( @PathVariable("idReservation") Long id);

    @GetMapping(value = APP_ROOT +"/Reservations/{DateReservation}",produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationDto findReservationByDateReservation(@PathVariable("DateReservation") LocalDate DateReservation);

    @GetMapping(value = APP_ROOT +"/Reservations/update/{idReservation}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationDto updateReservation(@RequestBody ReservationDto Reservation,@PathVariable("idReservation") Long id);

    @GetMapping(value = APP_ROOT +"/Reservations/delete/{idReservation}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteReservation(@PathVariable("idReservation") Long id);

    @GetMapping(value = APP_ROOT +"/Reservations/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<ReservationDto> getReservations(@RequestParam("page") int page,@RequestParam("size") int size);


}
