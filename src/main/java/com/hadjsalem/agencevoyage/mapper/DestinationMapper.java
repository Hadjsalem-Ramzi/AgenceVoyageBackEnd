package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import com.hadjsalem.agencevoyage.entities.Destination;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DestinationMapper {
    private ModelMapper mapper;

    @Autowired
    public DestinationMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public DestinationDto  fromdestination(Destination destination){
        return  mapper.map(destination,DestinationDto.class);
    }

    public Destination fromdestinationDto(DestinationDto destinationDto){
        return mapper.map(destinationDto,Destination.class);
    }
}
