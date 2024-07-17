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
        moyenTransportRepository.save(MoyenTransport.builder().nom("Autobus-10").Type("Terrestre").capacite(100L).build());
    }

    @Test
    public  void ShouldFindMoyenTransportBy(){
        String nom="Autobus-10";

        Optional<MoyenTransport> result = moyenTransportRepository.findMoyenTransportByNom(nom);

        AssertionsForClassTypes.assertThat(result).isPresent();
    }

    @Test
    public  void ShouldNotFindMoyenTransportBy(){
        String nom="ncvbcbc";

        Optional<MoyenTransport> result = moyenTransportRepository.findMoyenTransportByNom(nom);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }


}
