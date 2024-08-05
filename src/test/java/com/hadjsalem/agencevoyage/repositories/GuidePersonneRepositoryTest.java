package com.hadjsalem.agencevoyage.repositories;

import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuidePersonneRepositoryTest {
    @Autowired
    private GuidePersonneRepository guidePersonneRepository;

    @BeforeEach
    public void SetUp(){
        guidePersonneRepository.save(GuidePersonne.builder().firstName("Monji").lastName("hajsalem").numTel(65213489).build());
        guidePersonneRepository.save(GuidePersonne.builder().firstName("Karim").lastName("Ben Mostfa").numTel(65583489).build());
    }

    @Test
    public void ShouldFindPersonneByNumTel(){
        Integer NumTel=65213489;

        Optional<GuidePersonne> result=guidePersonneRepository.findPersonneByNumTelephone(NumTel);

        AssertionsForClassTypes.assertThat(result).isPresent();

    }

    @Test
    public void ShouldNotFindPersonneByNumTel(){
        Integer NumTel=65454444;

        Optional<GuidePersonne> result=guidePersonneRepository.findPersonneByNumTelephone(NumTel);

        AssertionsForClassTypes.assertThat(result).isEmpty();

    }

}
