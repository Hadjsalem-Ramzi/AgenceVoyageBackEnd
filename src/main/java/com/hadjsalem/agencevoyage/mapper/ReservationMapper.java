package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ReservationDto;
import com.hadjsalem.agencevoyage.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationMapper {
    private ModelMapper mapper;

    @Autowired
    public ReservationMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ReservationDto fromReservation(Reservation reservation){
        return  mapper.map(reservation,ReservationDto.class);
    }

    public Reservation fromReservationDto(ReservationDto reservationDto){
        return  mapper.map(reservationDto,Reservation.class);
    }
}
