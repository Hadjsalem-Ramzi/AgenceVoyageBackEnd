package com.hadjsalem.agencevoyage.mapper;


import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChauffeurMapperTest {
    private final ModelMapper modelMapper = new ModelMapper();
    private ChauffeurMapper undetTest= new ChauffeurMapper(modelMapper);
    @Test
    public void ShouldMapChauffeurToChauffeurDto(){

        Chauffeur givenChauffeur= new Chauffeur(1L,"Ramzi","Hadjsalem",54612458L);
        ChauffeurDto expected= new ChauffeurDto(1L,"Ramzi","Hadjsalem",54612458L);

        ChauffeurDto result= undetTest.fromChaufeur(givenChauffeur);


        assertThat(expected).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

}
