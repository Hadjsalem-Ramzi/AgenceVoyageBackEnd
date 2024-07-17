package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.entities.Client;
import com.hadjsalem.agencevoyage.mapper.ClientMapper;
import com.hadjsalem.agencevoyage.repositories.ClientRepository;
import com.hadjsalem.agencevoyage.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ClientMapper mapper;


    @Override
    public ClientDto findClientById(Long id) {
    Optional<Client> optionalClient =clientRepository.findById(id);
    return optionalClient.map(mapper::fromClient).orElseThrow(()->new NoSuchElementException("Client Not Found"));

    }

    public ClientDto findClientByFirstName(String firstName) {
        Optional<Client> client = clientRepository.findClientByFirstName(firstName);
        if (!client.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromClient(client.get());
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
      Client client1 = mapper.fromClientDto(clientDto);
      Client client2= clientRepository.save(client1);
      return mapper.fromClient(client2);
    }

    @Override
    public ClientDto updateClient(ClientDto clientdto, Long id) {
      Optional<Client> client1 = clientRepository.findById(id);
      if(client1.isPresent()){
      Client client2 = client1.get();
      Client client3= clientRepository.saveAndFlush(client2);
      return mapper.fromClient(client3);
      }else {
          throw  new NoSuchElementException("Client NotFound");
      }
    }

    @Override
    public void deleteClient(Long id) {
       clientRepository.deleteById(id);
    }
}
