package com.hadjsalem.agencevoyage.repositories;


import com.hadjsalem.agencevoyage.entities.Vehicule;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehiculeRepositoryTest {

    @Autowired
    private  VehiculeRepository vehiculeRepository;

    @BeforeEach
    public void SetUp(){
        vehiculeRepository.save(Vehicule.builder().type("Marchedes").capacite(400).immatricule("574-Tunis-8456").build());
    }

    @Test
    public void ShouldFindVehiculeByImmatricule(){
        String Immatricule="574-Tunis-8456";

        Optional<Vehicule> result = vehiculeRepository.findVehiculeByImmatricule(Immatricule);

        AssertionsForClassTypes.assertThat(result).isPresent();

    }


    @Test
    public void ShouldNotFindVehiculeByImmatricule(){
        String Immatricule="574-Tunis-84456";

        Optional<Vehicule> result = vehiculeRepository.findVehiculeByImmatricule(Immatricule);

        AssertionsForClassTypes.assertThat(result).isEmpty();

    }

}
