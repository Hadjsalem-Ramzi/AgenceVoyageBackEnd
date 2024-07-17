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
        transportCommunRepository.save(new TransportCommun(1,"AZERTY",54610235,100));
    }

    @Test
    public void SouldFindTransportCommunByNom(){
        String nom ="AZERTY";

        Optional<TransportCommun> result = transportCommunRepository.findTransportCommunByNom(nom);

      AssertionsForClassTypes.assertThat(result).isPresent();

    }

    @Test
    public void SouldNotFindTransportCommunByNom(){
        String nom ="Anhgfd";

        Optional<TransportCommun> result = transportCommunRepository.findTransportCommunByNom(nom);

        AssertionsForClassTypes.assertThat(result).isEmpty();

    }

}
