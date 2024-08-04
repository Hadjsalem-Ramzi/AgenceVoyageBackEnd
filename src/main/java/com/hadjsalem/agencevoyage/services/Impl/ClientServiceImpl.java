package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import com.hadjsalem.agencevoyage.entities.Client;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.ClientMapper;
import com.hadjsalem.agencevoyage.repositories.ClientRepository;
import com.hadjsalem.agencevoyage.services.ClientService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ClientMapper mapper;
    private ObjectsValidators<Client> clientValidators;

    @Override
    public ClientDto findClientById(Long id) {
    Optional<Client> optionalClient =clientRepository.findById(id);
    return optionalClient.map(mapper::fromClient).orElseThrow(()->new EntityNotFoundException("Client Not Found"));

    }

    @Override
    public ClientDto findClientByEmail(String email) {
        Optional<Client> client = clientRepository.findClientByEmail(email);
        if(client.isEmpty()){
            throw  new EntityNotFoundException("client not Found");
        }
        return mapper.fromClient(client.get());
    }

    public ClientDto findClientByFirstName(String email) {
        Optional<Client> client = clientRepository.findClientByEmail(email);
        if (!client.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromClient(client.get());
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
      Client client = mapper.fromClientDto(clientDto);
      if(client == null){
          throw new IllegalArgumentException("Client est null");
      }
      
      //validation des champs
        clientValidators.validate(client);
       boolean exists = clientRepository.existsByEmail(clientDto.getEmail());
       if(exists){
           throw new DuplicateEntryException("un client est existe avec cette email");
       }
       Client savedClient = clientRepository.save(client);
       return mapper.fromClient(savedClient);
    }

    @Override
    public ClientDto updateClient(ClientDto clientdto, Long id) {
      Optional<Client> client1 = clientRepository.findById(id);
      if(client1.isPresent()){
      Client client2 = client1.get();
      client2.setId(id);
      clientValidators.validate(client2);
      if (clientRepository.existsByEmail(client2.getEmail())){
          throw  new DuplicateEntryException(" un client est existe avec cette email");
      }
      Client client3= clientRepository.saveAndFlush(client2);
      return mapper.fromClient(client3);
      }else {
          throw  new EntityNotFoundException("Client NotFound");
      }
    }

    @Override
    public void deleteClient(Long id) {
        if(clientRepository.findById(id).isPresent()){
            clientRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("client with this is Not Found");
        }
       
    }

    public PageResponse<ClientDto> getClients(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Client> Clients = clientRepository.findAll(pageRequest);
        List<ClientDto> ClientList = Clients.map(mapper::fromClient).getContent();


        return new PageResponse<>(
                ClientList,
                Clients.getNumber(),
                Clients.getSize(),
                Clients.getTotalElements(),
                Clients.getTotalPages(),
                Clients.isFirst(),
                Clients.isLast()
        );
    }



    }
