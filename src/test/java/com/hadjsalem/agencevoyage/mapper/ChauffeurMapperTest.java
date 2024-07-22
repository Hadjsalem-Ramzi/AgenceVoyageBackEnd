package com.hadjsalem.agencevoyage.mapper;


import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChauffeurMapperTest {

    @Autowired
    ChauffeurMapper underTest  ;
   @Test
    public void ShouldMapChauffeurToChauffeurDto(){

        Chauffeur givenChauffeur= Chauffeur.builder().firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022L).build();
        ChauffeurDto expected = ChauffeurDto.builder().firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022L).build();

        ChauffeurDto result= underTest.fromChaufeur(givenChauffeur);

        assertThat(expected).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldMapChauffeurDtoToChauffeur(){

        ChauffeurDto givenChauffeur= ChauffeurDto.builder().firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022L).build();
        Chauffeur expected = Chauffeur.builder().firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022L).build();

        Chauffeur result= underTest.fromChauffeurDto(givenChauffeur);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldNotMapNullChauffeurToChauffeurDto(){
       Chauffeur givenChauffeur= null;

        assertThatThrownBy(()->underTest.fromChaufeur(givenChauffeur)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void ShouldNotMapNullChauffeurDtoToChauffeur(){
        ChauffeurDto givenChauffeur= null;

        assertThatThrownBy(()->underTest.fromChauffeurDto(givenChauffeur)).isInstanceOf(IllegalArgumentException.class);

    }


}
