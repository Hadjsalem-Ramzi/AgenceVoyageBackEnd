package com.hadjsalem.agencevoyage.repositories;


import com.hadjsalem.agencevoyage.entities.Hotel;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HotelRepositoryTest {
    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    public void SetUp(){
        hotelRepository.save(Hotel.builder().libelle("Hanibaal").caracteristique("*****").numberBed(500).build());
    }

  @Test
    public void ShouldFindHotelByLibelle(){
        String Libelle="Hanibaal";

      Optional<Hotel> result = hotelRepository.findHotelByLibelle(Libelle);

      AssertionsForClassTypes.assertThat(result).isPresent();

  }


    @Test
    public void ShouldNotFindHotelByLibelle(){
        String Libelle="Florida";

        Optional<Hotel> result = hotelRepository.findHotelByLibelle(Libelle);

        AssertionsForClassTypes.assertThat(result).isEmpty();

    }



}
