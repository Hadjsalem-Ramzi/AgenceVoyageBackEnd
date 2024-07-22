package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuidePapierMapperTest {

    @Autowired
    GuidePapierMapper underTest;

    @Test
    public  void ShouldMapGuidePapierToGuidePapierDto(){
        GuidePapier givenGuidePapier = GuidePapier.builder().libelle("CartePapierFrance").build();
        GuidePapierDto expected = GuidePapierDto.builder().libelle("CartePapierFrance").build();

        GuidePapierDto result = underTest.fromGuidePapier(givenGuidePapier);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public  void ShouldMapGuidePapierDtoToGuidePapier(){
        GuidePapierDto givenGuidePapier = GuidePapierDto.builder().libelle("CartePapierFrance").build();
        GuidePapier expected = GuidePapier.builder().libelle("CartePapierFrance").build();

        GuidePapier result = underTest.fromGuidePapierDto(givenGuidePapier);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public  void ShouldNotMapNullGuidePapierToGuidePapierDto(){
        GuidePapier givenGuidePapier= null;

        assertThatThrownBy(()->underTest.fromGuidePapier(givenGuidePapier)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public  void ShouldNotMapNullGuidePapierDtoToGuidePapier(){
        GuidePapierDto givenGuidePapier= null;

        assertThatThrownBy(()->underTest.fromGuidePapierDto(givenGuidePapier)).isInstanceOf(IllegalArgumentException.class);

    }

}
