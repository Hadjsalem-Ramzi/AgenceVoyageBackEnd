package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class CompagnieTransportMapper {
    private ModelMapper mapper;

    public CompagnieTransportDto fromCompagnieTransport(CompagnieTransport compagnieTransport){
        return  mapper.map(compagnieTransport,CompagnieTransportDto.class);
    }
    public CompagnieTransport fromCompagnieTransportDto(CompagnieTransportDto compagnieTransportDto){
        return  mapper.map(compagnieTransportDto,CompagnieTransport.class);
    }

}
