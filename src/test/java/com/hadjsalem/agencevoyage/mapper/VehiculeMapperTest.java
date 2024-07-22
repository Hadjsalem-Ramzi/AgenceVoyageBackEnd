package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import com.hadjsalem.agencevoyage.entities.Vehicule;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehiculeMapperTest {

    @Autowired
    VehiculeMapper underTest ;

    @Test
    public void ShouldMapVehiculeToVehiculeDto(){
        Vehicule givenVehicule = Vehicule.builder().immatricule("145-Tunis-487").type("Volswagen").capacite(140).build();
        VehiculeDto expected = VehiculeDto.builder().immatricule("145-Tunis-487").type("Volswagen").capacite(140).build();

        VehiculeDto result = underTest.fromVehicule(givenVehicule);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldMapVehiculeDtoToVehicule(){
        VehiculeDto givenVehicule = VehiculeDto.builder().immatricule("145-Tunis-487").type("Volswagen").capacite(140).build();
        Vehicule expected = Vehicule.builder().immatricule("145-Tunis-487").type("Volswagen").capacite(140).build();

        Vehicule result = underTest.fromVehiculeDto(givenVehicule);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ShouldNotMapNullVehiculeToVhiculeDto(){
        Vehicule givenVehicule= null;
        assertThatThrownBy(()-> underTest.fromVehicule(givenVehicule)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullVehiculeDtoToVhicule(){
        VehiculeDto givenVehicule= null;
        assertThatThrownBy(()-> underTest.fromVehiculeDto(givenVehicule)).isInstanceOf(IllegalArgumentException.class);
    }


}
