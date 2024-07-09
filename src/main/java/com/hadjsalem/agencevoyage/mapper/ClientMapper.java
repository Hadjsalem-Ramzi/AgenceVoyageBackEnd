package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.entities.Client;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ClientMapper {
    private ModelMapper mapper;

    public ClientDto fromClient(Client client){
        return mapper.map(client,ClientDto.class);
    }

    public Client fromClientDto(ClientDto clientDto){
        return mapper.map(clientDto,Client.class);
    }

}
