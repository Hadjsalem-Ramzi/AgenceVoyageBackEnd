package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MoyenTransportRepositoryTest {

    @Autowired
    private MoyenTransportRepository  moyenTransportRepository;

    @BeforeEach
    public void SetUp(){
        moyenTransportRepository.save(MoyenTransport.builder().name("Hannibaal").Type("Terrseetre").capacite(500L).build());
    }

    @Test
    public  void ShouldFindMoyenTransportBy(){
        String name="Autobus-10";

        Optional<MoyenTransport> result = moyenTransportRepository.findMoyenTransportByName(name);

        AssertionsForClassTypes.assertThat(result).isPresent();
    }

    @Test
    public  void ShouldNotFindMoyenTransportBy(){
        String name="Autobus-10";

        Optional<MoyenTransport> result = moyenTransportRepository.findMoyenTransportByName(name);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }


}
