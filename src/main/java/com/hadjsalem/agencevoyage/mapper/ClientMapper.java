package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.entities.Client;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientMapper {

    private ModelMapper mapper;

    @Autowired
    public ClientMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ClientDto fromClient(Client client){
        return mapper.map(client,ClientDto.class);
    }

    public Client fromClientDto(ClientDto clientDto){
        return mapper.map(clientDto,Client.class);
    }

}
