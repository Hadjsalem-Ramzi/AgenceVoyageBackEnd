package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GuidePapierMapper {
    private ModelMapper mapper;


    @Autowired
    public GuidePapierMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public GuidePapierDto fromGuidePapier(GuidePapier guidePapier){
        return  mapper.map(guidePapier,GuidePapierDto.class);
    }

    public GuidePapier fromGuidePapierDto(GuidePapierDto guidePapierDto){
        return mapper.map(guidePapierDto,GuidePapier.class);
    }
}

