package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;
import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class SocieteLocationMapper {
    private ModelMapper mapper;

    public SocieteLocationDto fromSocieteLocation(SocieteLocation societeLocation){
        return  mapper.map(societeLocation,SocieteLocationDto.class);
    }

    public SocieteLocation fromSocieteLocationDto(SocieteLocationDto societeLocationDto){
        return  mapper.map(societeLocationDto,SocieteLocation.class);
    }
}
