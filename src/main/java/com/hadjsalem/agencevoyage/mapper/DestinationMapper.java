package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import com.hadjsalem.agencevoyage.entities.Destination;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class DestinationMapper {
    private ModelMapper mapper;

    public DestinationDto  fromdestination(Destination destination){
        return  mapper.map(destination,DestinationDto.class);
    }

    public Destination fromdestinationDto(DestinationDto destinationDto){
        return mapper.map(destinationDto,Destination.class);
    }
}
