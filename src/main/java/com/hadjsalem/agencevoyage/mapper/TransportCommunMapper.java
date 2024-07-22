package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import com.hadjsalem.agencevoyage.entities.TransportCommun;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransportCommunMapper {
    private ModelMapper mapper;

    @Autowired
    public TransportCommunMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public TransportCommunDto fromTransportCommun(TransportCommun transportCommun){
        return  mapper.map(transportCommun,TransportCommunDto.class);
    }

    public  TransportCommun fromTransportCommunDto(TransportCommunDto transportCommunDto){
        return  mapper.map(transportCommunDto,TransportCommun.class);
    }

}
