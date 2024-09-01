package com.hadjsalem.agencevoyage.mapper;
import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import com.hadjsalem.agencevoyage.entities.TransportCommun;
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
        TransportCommun givenTransportCommun =TransportCommun.builder().id(1L).name("Autobus").numTel(54604022).build();

        TransportCommunDto expected =TransportCommunDto.builder().id(1L).name("Autobus").numTel(54604022).build();

        TransportCommunDto result = underTest.fromTransportCommun(givenTransportCommun);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void ShouldMapTransportCommunDtoToTransportCommun(){
        TransportCommunDto givenTransportCommunDto =TransportCommunDto.builder().id(1L).name("Autobus").numTel(54604022).build();
        TransportCommun expected =TransportCommun.builder().id(1L).name("Autobus").numTel(54604022).build();

        TransportCommun result = underTest.fromTransportCommunDto(givenTransportCommunDto);

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

