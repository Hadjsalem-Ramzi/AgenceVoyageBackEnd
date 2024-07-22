package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.FactureDto;
import com.hadjsalem.agencevoyage.entities.Facture;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FactureMapperTest {

    @Autowired
    FactureMapper unserTest;

    @Test
    public  void ShouldMapFactureToFactureDto(){

        Facture givenFacture = Facture.builder().designation("Facture1").quantite(5L).prixTotal(7900.500).prixUnitaire(3000.2).Total(60000.2).build();
        FactureDto expected = FactureDto.builder().designation("Facture1").quantite(5L).prixTotal(7900.500).prixUnitaire(3000.2).Total(60000.2).build();

         FactureDto result = unserTest.fromFacture(givenFacture);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public  void ShouldMapFactureDtoToFacture(){

        FactureDto givenFacture = FactureDto.builder().designation("Facture1").quantite(5L).prixTotal(7900.500).prixUnitaire(3000.2).Total(60000.2).build();
        Facture expected = Facture.builder().designation("Facture1").quantite(5L).prixTotal(7900.500).prixUnitaire(3000.2).Total(60000.2).build();

        Facture result = unserTest.fromFactureDto(givenFacture);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldNotMapNullFactureToFactureDto(){
       Facture givenFacture = null;

        assertThatThrownBy(()->unserTest.fromFacture(givenFacture)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullFactureDtoToFacture(){
        FactureDto givenFacture = null;

        assertThatThrownBy(()->unserTest.fromFactureDto(givenFacture)).isInstanceOf(IllegalArgumentException.class);
    }

}
