package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.HotelDto;
import com.hadjsalem.agencevoyage.entities.Hotel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HotelMapper {
    private ModelMapper mapper;

    @Autowired
    public HotelMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public HotelDto fromHotel(Hotel hotel){
        return mapper.map(hotel,HotelDto.class);
    }

    public Hotel fromHotelDto(HotelDto hotelDto){
        return  mapper.map(hotelDto,Hotel.class);
    }
}
