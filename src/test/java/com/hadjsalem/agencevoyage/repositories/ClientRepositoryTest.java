package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Client;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void SetUp(){
        clientRepository.save(Client.builder().firstName("Houssem").lastName("hadjsalem").password("jhdggdk").email("houssem@gmail.com").build());
        clientRepository.save(Client.builder().firstName("Ali").lastName("hadjsalem").password("jhdghjkk").email("Ali@gmail.com").build());
        clientRepository.save(Client.builder().firstName("Rayen").lastName("hadjsalem").password("hgdjkhdgtxb").email("Rayen@gmail.com").build());
    }

    @Test
    public void SouldFindClientByFirstName(){
        String firstName="Houssem";
        Optional<Client> result=clientRepository.findClientByFirstName(firstName);
        AssertionsForClassTypes.assertThat(result).isPresent();
    }
    @Test
    public void SouldNotFindClientByFirstName(){
        String firstName="ccccccccc";

        Optional<Client> result=clientRepository.findClientByFirstName(firstName);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }



}
