package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuidePersonneMapperTest {

    @Autowired
    GuidePersonneMapper underTest;

    @Test
    public void ShouldMapGuidePersonneToGuidePersonneDto(){
        GuidePersonne givenPersonne = GuidePersonne.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto expected = GuidePersonneDto.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        GuidePersonneDto result = underTest.fromGuidePersonne(givenPersonne);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldMapGuidePersonneDtoToGuidePersonne(){
        GuidePersonneDto givenPersonne = GuidePersonneDto.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonne expected = GuidePersonne.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        GuidePersonne result = underTest.fromGuidePersonneDto(givenPersonne);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public  void ShouldNotMapNullGuidePersonneToGuidePersonneDto(){
        GuidePersonne givenGuidePersonne= null;

        assertThatThrownBy(()->underTest.fromGuidePersonne(givenGuidePersonne)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public  void ShouldNotMapNullGuidePersonneDtoToGuidePersonne(){
        GuidePersonneDto givenGuidePersonne= null;

        assertThatThrownBy(()->underTest.fromGuidePersonneDto(givenGuidePersonne)).isInstanceOf(IllegalArgumentException.class);

    }


}
