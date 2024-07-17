package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.Destination;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DestinationRepositoryTest {
    @Autowired
    private DestinationRepository destinationRepository;

    @BeforeEach
    public void SetUp(){
        destinationRepository.save(Destination.builder().pays("France").ville("Toulouse").build());
        destinationRepository.save(Destination.builder().pays("Spain").ville("Madrid").build());
        destinationRepository.save(Destination.builder().pays("Belgium").ville("Bruxel").build());
    }

    @Test
    public void ShouldFindDestinationByVille(){
      String ville="Toulouse";
        Optional<Destination> result = destinationRepository.findDestinationByVille(ville);

        AssertionsForClassTypes.assertThat(result).isPresent();
    }

    @Test
    public void ShouldNotFindDestinationByVille(){
        String ville="Tnbghklmlml";
        Optional<Destination> result = destinationRepository.findDestinationByVille(ville);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }




}
