package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import com.hadjsalem.agencevoyage.entities.Destination;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DestinationMapperTest {

    @Autowired
    DestinationMapper underTest;

    @Test
    public  void ShouldMapDestinatioToDestinationDto(){

        Destination givenDestination = Destination.builder().ville("Sapin").ville("Barcelone").build();
        DestinationDto expected = DestinationDto.builder().ville("Sapin").ville("Barcelone").build();

        DestinationDto result=underTest.fromdestination(givenDestination);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    public  void ShouldMapDestinationDtoToDestination(){

        DestinationDto givenDestination = DestinationDto.builder().ville("Sapin").ville("Barcelone").build();
        Destination expected = Destination.builder().ville("Sapin").ville("Barcelone").build();

        Destination result=underTest.fromdestinationDto(givenDestination);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldNotMapNullDestinationToDestinationDto(){
        Destination givenDestnation= null;

        assertThatThrownBy(()->underTest.fromdestination(givenDestnation)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullDestinationDtoToDestination(){
        DestinationDto givenDestnation= null;

        assertThatThrownBy(()->underTest.fromdestinationDto(givenDestnation)).isInstanceOf(IllegalArgumentException.class);
    }

}
