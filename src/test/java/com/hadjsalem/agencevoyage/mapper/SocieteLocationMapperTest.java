package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;
import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SocieteLocationMapperTest {

    @Autowired
    SocieteLocationMapper underTest;

    @Test
    public void ShouldMapSocieteLocationToSocieteLocationDto(){

        SocieteLocation givenSocieteLocation = SocieteLocation.builder().id(1L).name("Ramzi").numTel(78562354).build();
        SocieteLocationDto  expected = SocieteLocationDto.builder().id(1L).name("Ramzi").numTel(78562354).build();

        SocieteLocationDto result = underTest.fromSocieteLocation(givenSocieteLocation);

        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }


    @Test
    public void ShouldMapSocieteLocationDtoToSocieteLocation(){

        SocieteLocationDto  givenLocationDto = SocieteLocationDto.builder().id(1L).name("Ramzi").numTel(78562354).build();


        SocieteLocation expected = SocieteLocation.builder().id(1L).name("Ramzi").numTel(78562354).build();

        SocieteLocation result = underTest.fromSocieteLocationDto(givenLocationDto);

        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void ShouldNotMapNullSocieteLocationToSocieteLocationDto(){
        SocieteLocation givenSocieteLocation = null;

        assertThatThrownBy(()-> underTest.fromSocieteLocation(givenSocieteLocation)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullSocieteLocationDtoToSocieteLocation(){
        SocieteLocationDto givenSocieteLocation = null;

        assertThatThrownBy(()-> underTest.fromSocieteLocationDto(givenSocieteLocation)).isInstanceOf(IllegalArgumentException.class);
    }


}
