package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompagnieTransportMapperTest {

    @Autowired
    CompagnieTransportMapper underTest;

    @Test
    public void ShouldMapCompagnieTransportToCompagnieTransportDto(){
        CompagnieTransport givenCompagnieTransport = CompagnieTransport.builder().nom("Carthage").numTel(54625897).build();
        CompagnieTransportDto expected= CompagnieTransportDto.builder().nom("Carthage").numTel(54625897).build();

        CompagnieTransportDto result = underTest.fromCompagnieTransport(givenCompagnieTransport);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ShouldMapCompagnieTransportDtoToCompagnieTransport(){
        CompagnieTransportDto givenCompagnieTransport = CompagnieTransportDto.builder().nom("Carthage").numTel(54625897).build();
        CompagnieTransport expected= CompagnieTransport.builder().nom("Carthage").numTel(54625897).build();

        CompagnieTransport result = underTest.fromCompagnieTransportDto(givenCompagnieTransport);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

  @Test
    public void ShouldNotMapNullCompagnieTransportToCompagnieTransportDto(){
        CompagnieTransport givenCompagnieTransport = null;

        assertThatThrownBy(()-> underTest.fromCompagnieTransport(givenCompagnieTransport)).isInstanceOf(IllegalArgumentException.class);
  }
    @Test
    public void ShouldNotMapNullCompagnieTransportDtoToCompagnieTransport(){
        CompagnieTransportDto givenCompagnieTransport = null;

        assertThatThrownBy(()-> underTest.fromCompagnieTransportDto(givenCompagnieTransport)).isInstanceOf(IllegalArgumentException.class);
    }

}
