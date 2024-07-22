package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.ItineraireDto;
import com.hadjsalem.agencevoyage.entities.Itineraire;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItineraireMapperTest {

    @Autowired
    ItineraireMapper underTest;

    @Test
    public void ShouldMapIteneraireToItenraireDto(){
        Itineraire givenItineraire = Itineraire.builder().libelle("Itinéraire Paris-Londres").caracteristique("Itinéraire avec des arrêts dans les principales villes").build();
        ItineraireDto expected = ItineraireDto.builder().libelle("Itinéraire Paris-Londres").caracteristique("Itinéraire avec des arrêts dans les principales villes").build();

        ItineraireDto result = underTest.fromItineraire(givenItineraire);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    public void ShouldMapIteneraireDtoToItenraire(){
        ItineraireDto givenItineraire = ItineraireDto.builder().libelle("Itinéraire Paris-Londres").caracteristique("Itinéraire avec des arrêts dans les principales villes").build();
        Itineraire expected = Itineraire.builder().libelle("Itinéraire Paris-Londres").caracteristique("Itinéraire avec des arrêts dans les principales villes").build();

        Itineraire result = underTest.fromItineraireDto(givenItineraire);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ShouldNotMapNullIteineraireToItineraireDto(){
        Itineraire givenItineraire=null;

        assertThatThrownBy(()-> underTest.fromItineraire(givenItineraire)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullIteineraireDtoToItineraire(){
        ItineraireDto givenItineraire=null;

        assertThatThrownBy(()-> underTest.fromItineraireDto(givenItineraire)).isInstanceOf(IllegalArgumentException.class);
    }

}
