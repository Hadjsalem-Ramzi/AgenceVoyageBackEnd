package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.HotelDto;
import com.hadjsalem.agencevoyage.entities.Hotel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class HotelMapper {
    private ModelMapper mapper;

    public HotelDto fromHotel(Hotel hotel){
        return mapper.map(hotel,HotelDto.class);
    }

    public Hotel fromHotelDto(HotelDto hotelDto){
        return  mapper.map(hotelDto,Hotel.class);
    }
}
