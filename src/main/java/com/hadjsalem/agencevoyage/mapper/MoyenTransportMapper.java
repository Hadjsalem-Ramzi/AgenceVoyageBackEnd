package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MoyenTransportMapper {
    private ModelMapper mapper;

    @Autowired
    public MoyenTransportMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public MoyenTransportDto fromMoyenTransport(MoyenTransport moyenTransport){
        return mapper.map(moyenTransport,MoyenTransportDto.class);
    }

    public MoyenTransport fromMoyenTransportDto(MoyenTransportDto moyenTransportDto){
        return  mapper.map(moyenTransportDto,MoyenTransport.class);
    }

}
