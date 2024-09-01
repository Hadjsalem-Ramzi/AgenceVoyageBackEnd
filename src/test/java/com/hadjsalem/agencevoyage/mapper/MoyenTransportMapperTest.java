package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MoyenTransportMapperTest {

    @Autowired
    MoyenTransportMapper underTest;

    @Test
    public void ShouldMapMoyenTransportToMoyenTransportDto(){
        MoyenTransport givenMoyenTransport = MoyenTransport.builder().name("Bus Touristique").Type("Terrestre").capacite(100L).build();
        MoyenTransportDto expected = MoyenTransportDto.builder().name("Bus Touristique").Type("Terrestre").capacite(100L).build();

        MoyenTransportDto result= underTest.fromMoyenTransport(givenMoyenTransport);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ShouldMapMoyenTransportDtoToMoyenTransport(){
        MoyenTransport givenMoyenTransport = MoyenTransport.builder().name("Bus Touristique").Type("Terrestre").capacite(100L).build();
        MoyenTransportDto expected = MoyenTransportDto.builder().name("Bus Touristique").Type("Terrestre").capacite(100L).build();

        MoyenTransportDto result= underTest.fromMoyenTransport(givenMoyenTransport);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ShouldNotMapNullMoyenTransportToMouenTarnsportDto(){
        MoyenTransport givenMoyenTarnsport=null;

        assertThatThrownBy(()-> underTest.fromMoyenTransport(givenMoyenTarnsport)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullMoyenTransportDtoToMouenTarnsport(){
        MoyenTransportDto givenMoyenTarnsport=null;

        assertThatThrownBy(()-> underTest.fromMoyenTransportDto(givenMoyenTarnsport)).isInstanceOf(IllegalArgumentException.class);
    }


}
