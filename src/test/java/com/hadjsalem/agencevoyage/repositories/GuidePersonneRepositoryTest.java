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
        guidePersonneRepository.save(GuidePersonne.builder().name("Ramzi").build());
        guidePersonneRepository.save(GuidePersonne.builder().name("Ramzi").build());
    }

    @Test
    public void ShouldFindPersonneByName(){
        String name = "Ramzi";

        Optional<GuidePersonne> result=guidePersonneRepository.findGuidePersonneByName(name);

        AssertionsForClassTypes.assertThat(result).isPresent();

    }

    @Test
    public void ShouldNotFindPersonneByNumTel(){
        String name="Ramzi";

        Optional<GuidePersonne> result=guidePersonneRepository.findGuidePersonneByName(name);

        AssertionsForClassTypes.assertThat(result).isEmpty();

    }

}
