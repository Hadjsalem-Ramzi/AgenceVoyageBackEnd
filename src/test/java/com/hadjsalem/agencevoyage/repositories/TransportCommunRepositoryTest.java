package com.hadjsalem.agencevoyage.repositories;


import com.hadjsalem.agencevoyage.entities.TransportCommun;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransportCommunRepositoryTest {
    @Autowired
    private TransportCommunRepository transportCommunRepository;

    @BeforeEach
    public void SetUp(){
        TransportCommun transportCommun = TransportCommun.builder().id(1L).name("AZERTY").numTel(54610235).build();
    }

    @Test
    public void SouldFindTransportCommunByNom(){
        String name ="AZERTY";

        Optional<TransportCommun> result = transportCommunRepository.findTransportCommunByName(name);

      AssertionsForClassTypes.assertThat(result).isPresent();

    }

    @Test
    public void SouldNotFindTransportCommunByNom(){
        String name ="Anhgfd";

        Optional<TransportCommun> result = transportCommunRepository.findTransportCommunByName(name);

        AssertionsForClassTypes.assertThat(result).isEmpty();

    }

}
