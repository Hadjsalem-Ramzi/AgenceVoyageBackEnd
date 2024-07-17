package com.hadjsalem.agencevoyage.repositories;


import com.hadjsalem.agencevoyage.entities.Itineraire;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItineraireRepositoryTest {
    @Autowired
    private ItineraireRepository itineraireRepository;

    @BeforeEach
    public void SetUp(){
        itineraireRepository.save(Itineraire.builder().libelle("Itenraire1").caracteristique("High level").build());
    }

    @Test
    public void SouldFindItenraireByLibelle(){
        String Libelle="Itenraire1";

        Optional<Itineraire> result=itineraireRepository.findItineraireByLibelle(Libelle);

        AssertionsForClassTypes.assertThat(result).isPresent();
    }
    @Test
    public void SouldNotFindItenraireByLibelle(){
        String Libelle="xxxxxx";

        Optional<Itineraire> result=itineraireRepository.findItineraireByLibelle(Libelle);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }
}
