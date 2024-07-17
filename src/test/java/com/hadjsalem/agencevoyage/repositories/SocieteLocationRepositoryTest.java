package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SocieteLocationRepositoryTest {
   @Autowired
  private SocieteLocationRepository societeLocationRepository;

   @BeforeEach
    public void SetUp(){
        societeLocationRepository.save(new SocieteLocation(1,"ABC",58741369));
   }

   @Test
    public void ShouldFindSocieteLocation(){
       String nom= "ABC";
       Optional<SocieteLocation> result = societeLocationRepository.findSocieteLocationByNom(nom);

       AssertionsForClassTypes.assertThat(result).isPresent();
   }

    @Test
    public void ShouldNotFindSocieteLocation(){
        String nom= "xxx";
        Optional<SocieteLocation> result = societeLocationRepository.findSocieteLocationByNom(nom);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }

}

