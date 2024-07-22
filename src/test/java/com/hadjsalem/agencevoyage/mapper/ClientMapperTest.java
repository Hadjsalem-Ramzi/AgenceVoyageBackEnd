package com.hadjsalem.agencevoyage.mapper;


import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.entities.Client;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientMapperTest {

    @Autowired
    ClientMapper underTest;

    @Test
   public void ShouldMapClientToClientDto(){

        Client givenClient= Client.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("gbfgtryfg").build();
        ClientDto  expected= ClientDto.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("gbfgtryfg").build();

        ClientDto result = underTest.fromClient(givenClient);

        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void ShouldMapClientDtoToClient(){

        ClientDto givenClient= ClientDto.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("gbfgtryfg").build();
        Client expected= Client.builder().firstName("Ramzi").lastName("Hadjsalem").email("hadjsalemramzi@gmail.com").password("gbfgtryfg").build();

        Client result = underTest.fromClientDto(givenClient);

        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void ShouldNotMapNullClientToClientDto(){
        Client givenClient = null;
        AssertionsForClassTypes.assertThatThrownBy(()-> underTest.fromClient(givenClient)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullClientDtoToClient(){
        ClientDto givenClient = null;
        AssertionsForClassTypes.assertThatThrownBy(()-> underTest.fromClientDto(givenClient)).isInstanceOf(IllegalArgumentException.class);
    }
}
