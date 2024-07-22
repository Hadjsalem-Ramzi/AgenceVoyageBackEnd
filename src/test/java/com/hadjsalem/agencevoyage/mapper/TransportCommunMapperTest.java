package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import com.hadjsalem.agencevoyage.entities.TransportCommun;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransportCommunMapperTest {

    @Autowired
    TransportCommunMapper underTest;

    @Test
    public void ShouldMapTransportCommunToTransportCommunDto(){
        TransportCommun givenTransportCommun = new TransportCommun(1,"Autobus",87654321,200);
        TransportCommunDto expected = new TransportCommunDto(1,"Autobus",87654321,200);

        TransportCommunDto result = underTest.fromTransportCommun(givenTransportCommun);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldMapTransportCommunDtoToTransportCommun(){
        TransportCommunDto givenTransportCommun = new TransportCommunDto(1,"Autobus",87654321,200);
        TransportCommun expected = new TransportCommun(1,"Autobus",87654321,200);

        TransportCommun result = underTest.fromTransportCommunDto(givenTransportCommun);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldNotMapNullTransportCommunToTransportCommunDto(){
        TransportCommun givenTransportCommun= null;

        assertThatThrownBy(()-> underTest.fromTransportCommun(givenTransportCommun)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ShouldNotMapNullTransportCommunDtoToTransportCommun(){
        TransportCommunDto givenTransportCommun= null;

        assertThatThrownBy(()-> underTest.fromTransportCommunDto(givenTransportCommun)).isInstanceOf(IllegalArgumentException.class);
    }

}
