package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompagnieTransportMapper {
    private ModelMapper mapper;

    @Autowired
    public CompagnieTransportMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CompagnieTransportDto fromCompagnieTransport(CompagnieTransport compagnieTransport){
        return  mapper.map(compagnieTransport,CompagnieTransportDto.class);
    }
    public CompagnieTransport fromCompagnieTransportDto(CompagnieTransportDto compagnieTransportDto){
        return  mapper.map(compagnieTransportDto,CompagnieTransport.class);
    }

}
