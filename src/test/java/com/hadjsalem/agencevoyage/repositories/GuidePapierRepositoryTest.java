package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePapier;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuidePapierRepositoryTest {
    @Autowired
    private GuidePapierRepository guidePapierRepository;
 
    @BeforeEach
    public void setUp(){
        guidePapierRepository.save(GuidePapier.builder().name("Guide1").build());
        guidePapierRepository.save(GuidePapier.builder().name("Guide2").build());
        guidePapierRepository.save(GuidePapier.builder().name( "Guide3").build());
    }

    @Test
    public void SouldFindGuidPapierByLibelle(){
        String name="Guide1";

        Optional<GuidePapier> result = guidePapierRepository.findGuidePapierByName(name);
        AssertionsForClassTypes.assertThat(result).isPresent();

    }

    @Test
    public void SouldNotFindGuidPapierByLibelle(){
        String name="Guide4";

        Optional<GuidePapier> result = guidePapierRepository.findGuidePapierByName(name);
        AssertionsForClassTypes.assertThat(result).isEmpty();

    }


}
