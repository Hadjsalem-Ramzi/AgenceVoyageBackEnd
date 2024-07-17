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
        guideRepository.save(Guide.builder().firstName("Ahmed").numTel(54601233).specialite("GuideTerrain").build());
        guideRepository.save(Guide.builder().firstName("Ali").numTel(54687933).specialite("GuideEnLigne").build());
    }

    @Test
  public void SouldFindGuideByNumTel(){
        Integer numTel=54601233;

        Optional<Guide> result= guideRepository.findGuideByNumTel(numTel);
        AssertionsForClassTypes.assertThat(result).isPresent();
    }

    @Test
    public void SouldNotFindGuideByNumTel(){
        Integer numTel=54601456;

        Optional<Guide> result= guideRepository.findGuideByNumTel(numTel);
        AssertionsForClassTypes.assertThat(result).isEmpty();
    }

}
