package com.hadjsalem.agencevoyage.repositories;


import com.hadjsalem.agencevoyage.entities.Guide;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuideRepositoryTest {
    @Autowired
     private GuideRepository guideRepository;

    @BeforeEach
    public void SetUp(){
        guideRepository.save(Guide.builder().name("Ramzi").build());
        guideRepository.save(Guide.builder().name("Ramzi").build());
    }

    @Test
  public void SouldFindGuideByNumTel(){
        String name = "Ramzi";

        Optional<Guide> result= guideRepository.findGuideByName(name);
        AssertionsForClassTypes.assertThat(result).isPresent();
    }

    @Test
    public void SouldNotFindGuideByNumTel(){
        String name = "Ramzi";

        Optional<Guide> result= guideRepository.findGuideByName(name);
        AssertionsForClassTypes.assertThat(result).isEmpty();
    }

}
