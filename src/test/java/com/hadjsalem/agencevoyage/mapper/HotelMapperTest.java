package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.HotelDto;
import com.hadjsalem.agencevoyage.entities.Hotel;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HotelMapperTest {

    @Autowired
    HotelMapper underTest;

    @Test
    public  void ShouldMapHotelToHotelDto(){
        Hotel givenHotel = Hotel.builder().libelle("Pyramide").caracteristique("****").numberBed(100).build();
        HotelDto expected  = HotelDto.builder().libelle("Pyramide").caracteristique("****").numberBed(100).build();
        HotelDto result = underTest.fromHotel(givenHotel);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public  void ShouldMapHotelDtoToHotel(){
        HotelDto givenHotel = HotelDto.builder().libelle("Pyramide").caracteristique("****").numberBed(100).build();
        Hotel expected  = Hotel.builder().libelle("Pyramide").caracteristique("****").numberBed(100).build();
        Hotel result = underTest.fromHotelDto(givenHotel);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldNotMapNullHotelToHotelDto(){
        Hotel givenHotel= null;

        assertThatThrownBy(()-> underTest.fromHotel(givenHotel)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullHotelDtoToHotel(){
        HotelDto givenHotel= null;

        assertThatThrownBy(()-> underTest.fromHotelDto(givenHotel)).isInstanceOf(IllegalArgumentException.class);
    }



}
