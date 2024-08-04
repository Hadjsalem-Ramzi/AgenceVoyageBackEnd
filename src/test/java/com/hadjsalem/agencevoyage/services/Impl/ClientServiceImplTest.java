/*
package com.hadjsalem.agencevoyage.services.Impl;


import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.entities.Client;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.ClientMapper;
import com.hadjsalem.agencevoyage.repositories.ClientRepository;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {


    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl underTest;

  @Mock
  private ObjectsValidators<Client> clientValidators;




    @Test
    void shouldSaveNewClient(){
        ClientDto clientDto=ClientDto.builder().firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();
        Client client=Client.builder().firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();
        Client savedClient= Client.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();
        ClientDto expectedClient= ClientDto.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").email("ramzi@gmail.com").password("123456").build();

        when(clientMapper.fromClientDto(clientDto)).thenReturn(client);
        when(clientRepository.existsByEmail(clientDto.getEmail())).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(savedClient);
        when(clientMapper.fromClient(savedClient)).thenReturn(expectedClient);
        Mockito.doNothing().when(clientValidators).validate(Mockito.any(Client.class));
        ClientDto result = underTest.saveClient(clientDto);
        assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(expectedClient);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenChauffeurExists() {
      Client  client = Client.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("123546").build();
      ClientDto  clientDto = ClientDto.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("123546").build();

      when(clientRepository.existsByEmail(clientDto.getEmail())).thenThrow( new DuplicateEntryException("client was Found with this Id"));

      AssertionsForClassTypes.assertThatThrownBy(()-> underTest.

    }






}






*/
