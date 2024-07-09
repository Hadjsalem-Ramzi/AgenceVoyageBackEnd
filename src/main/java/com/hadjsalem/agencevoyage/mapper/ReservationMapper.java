package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import com.hadjsalem.agencevoyage.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ReservationMapper {
    private ModelMapper mapper;

    public ReservationDto fromReservation(Reservation reservation){
        return  mapper.map(reservation,ReservationDto.class);
    }

    public Reservation fromReservationDto(ReservationDto reservationDto){
        return  mapper.map(reservationDto,Reservation.class);
    }
}
