package com.hadjsalem.agencevoyage.entities;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.TransportCommunMapper;
import com.hadjsalem.agencevoyage.repositories.TransportCommunRepository;
import com.hadjsalem.agencevoyage.services.TransportCommunService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransportCommunTest {

    @Mock
    private TransportCommunMapper transportCommunMapper;
    
    @Mock
    private TransportCommunRepository transportCommunRepository;
    
    @InjectMocks
    private TransportCommunService  underTest;
    
    
    @Mock
    private ObjectsValidators<TransportCommun> transportCommunValidators;

    @Test
    void shouldSaveNewtransportCommun(){
        TransportCommunDto transportCommunDto = TransportCommunDto.builder().name("LocationKia").numTel(98546213).build();
        TransportCommun transportCommun=  TransportCommun.builder().name("LocationKia").numTel(98546213).build();
        TransportCommun savedtransportCommun=  TransportCommun.builder().id(1L).name("LocationKia").numTel(98546213).build();
        TransportCommunDto expectedtransportCommun= TransportCommunDto.builder().id(1L).name("LocationKia").numTel(98546213).build();

        when(transportCommunMapper.fromTransportCommunDto(transportCommunDto)).thenReturn(transportCommun);
        when(transportCommunRepository.save(transportCommun)).thenReturn(savedtransportCommun);
        when(transportCommunMapper.fromTransportCommun(savedtransportCommun)).thenReturn(expectedtransportCommun);
        TransportCommunDto result = underTest.saveTransportCommun(transportCommunDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedtransportCommun);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhentransportCommunExists() {
        TransportCommunDto transportCommunDto = TransportCommunDto.builder().name("LocationKia").numTel(98546213).build();

        TransportCommun transportCommun = TransportCommun.builder().name("LocationKia").numTel(98546213).build();

        when(transportCommunMapper.fromTransportCommunDto(transportCommunDto)).thenReturn(transportCommun);
        when(transportCommunRepository.existsByNumTel(transportCommunDto.getNumTel())).thenReturn(true);
        Mockito.doNothing().when(transportCommunValidators).validate(Mockito.any(TransportCommun.class));

        assertThatThrownBy(() -> underTest.saveTransportCommun(transportCommunDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un transportCommun est existe avec ce nom");

        verify(transportCommunRepository, times(1)).existsByNumTel(transportCommun.getNumTel());
        verify(transportCommunRepository, never()).save(any(TransportCommun.class));
    }
    @Test
    void ShouldGetAlltransportCommuns() {
        TransportCommun transportCommun1 = new TransportCommun();
        transportCommun1.setId(1L);
        TransportCommun transportCommun2 = new TransportCommun();
        transportCommun2.setId(2L);
        List<TransportCommun> transportCommuns = Arrays.asList(transportCommun1, transportCommun2);
        Page<TransportCommun> pagetransportCommuns = new PageImpl<>(transportCommuns);

        TransportCommunDto transportCommunDto1 = new TransportCommunDto();
        transportCommunDto1.setId(1L);
        TransportCommunDto transportCommunDto2 = new TransportCommunDto();
        transportCommunDto2.setId(2L);

        when(transportCommunRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pagetransportCommuns);
        when(transportCommunMapper.fromTransportCommun(transportCommun1)).thenReturn(transportCommunDto1);
        when(transportCommunMapper.fromTransportCommun(transportCommun2)).thenReturn(transportCommunDto2);

        // When
        PageResponse<TransportCommunDto> result = underTest.getTransportCommuns(0, 2);

        // Then
        assertEquals(2, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getSize());
        assertEquals(true, result.isFirst());
        assertEquals(true, result.isLast());
    }
//***         Test GetById Method



    @Test
    void ShouldFindtransportCommunById() {
        Long giventransportCommunId = 1L;
        TransportCommun transportCommun = TransportCommun.builder().id(1L).name("LocationKia").numTel(98546213).build();
        TransportCommunDto expected = TransportCommunDto.builder().id(1L).name("LocationKia").numTel(98546213).build();
        when(transportCommunRepository.findById(giventransportCommunId)).thenReturn(Optional.of(transportCommun));
        when(transportCommunMapper.fromTransportCommun(transportCommun)).thenReturn(expected);
        TransportCommunDto result = underTest.findTransportCommunById(giventransportCommunId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindtransportCommunById() {
        Long giventransportCommunById = 5l;
        when(transportCommunRepository.findById(giventransportCommunById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findTransportCommunById(giventransportCommunById)).isInstanceOf(EntityNotFoundException.class).hasMessage("transportCommun Not Found");

    }

//******                Test Method FindtransportCommunByNumTel                    *********



    @Test
    void ShouldfindtransportCommunByLibelle() {
        String giventransportCommun = "LocationKia";
        TransportCommun transportCommun = TransportCommun.builder().name("LocationKia").numTel(98546213).build();
        TransportCommunDto expected = TransportCommunDto.builder().name("LocationKia").numTel(98546213).build();

        when(transportCommunRepository.findTransportCommunByName(giventransportCommun)).thenReturn(Optional.of(transportCommun));
        when(transportCommunMapper.fromTransportCommun(transportCommun)).thenReturn(expected);

        TransportCommunDto result = underTest.findTransportCommunByName(transportCommun.getName());

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindtransportCommunByVille() {
        String giventransportCommun = "LocationKia";
        when(transportCommunRepository.findTransportCommunByName(giventransportCommun)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findTransportCommunByName(giventransportCommun)).isInstanceOf(EntityNotFoundException.class);
    }


//**********    Test  Method updatetransportCommun ***




    @Test
    void ShouldUpdatetransportCommun() {
        Long giventransportCommunId = 2L;
        TransportCommun transportCommun = TransportCommun.builder().id(2L).name("LocationKia").numTel(98546213).build();
        TransportCommunDto transportCommunDto = TransportCommunDto.builder().id(2L).name("LocationKia").numTel(98546213).build();
        TransportCommun updatedtransportCommun = transportCommun.builder().id(2L).name("LocationKia").numTel(98546213).build();
        TransportCommunDto expected = transportCommunDto.builder().id(2L).name("LocationKia").numTel(98546213).build();

        when(transportCommunRepository.findById(giventransportCommunId)).thenReturn(Optional.of(transportCommun));
        when(transportCommunRepository.saveAndFlush(transportCommun)).thenReturn(updatedtransportCommun);
        when(transportCommunMapper.fromTransportCommun(updatedtransportCommun)).thenReturn(transportCommunDto);


        TransportCommunDto result = underTest.updateTransportCommun(transportCommunDto, giventransportCommunId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdatetransportCommun() {
        Long giventransportCommunId = 2L;
        TransportCommun transportCommun = TransportCommun.builder().id(2L).name("LocationKia").numTel(98546213).build();
        TransportCommunDto transportCommunDto = TransportCommunDto.builder().id(2L).name("LocationKia").numTel(98546213).build();
        TransportCommun updatedtransportCommun = transportCommun.builder().id(2L).name("LocationKia").numTel(98546213).build();
        TransportCommunDto expected = TransportCommunDto.builder().id(2L).name("LocationKia").numTel(98546213).build();

        when(transportCommunRepository.findById(giventransportCommunId)).thenReturn(Optional.of(transportCommun));
        when(transportCommunRepository.saveAndFlush(transportCommun)).thenReturn(updatedtransportCommun);
        when(transportCommunMapper.fromTransportCommun(updatedtransportCommun)).thenReturn(transportCommunDto);

        TransportCommunDto result = underTest.updateTransportCommun(transportCommunDto, giventransportCommunId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhentransportCommunNotFound() {
        Long giventransportCommunId = 2L;
        TransportCommunDto transportCommunDto = TransportCommunDto.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();

        when(transportCommunRepository.findById(giventransportCommunId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateTransportCommun(transportCommunDto, giventransportCommunId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long giventransportCommunId = 2L;
        TransportCommun transportCommun = TransportCommun.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();
        TransportCommunDto transportCommunDto = TransportCommunDto.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();

        when(transportCommunRepository.findById(giventransportCommunId)).thenReturn(Optional.of(transportCommun));
        when(transportCommunRepository.saveAndFlush(transportCommun)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateTransportCommun(transportCommunDto, giventransportCommunId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhentransportCommunDtoIsInvalid() {
        Long giventransportCommunId = 2L;
        TransportCommun transportCommun = TransportCommun.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();
        TransportCommunDto invalidtransportCommunDto = TransportCommunDto.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();

        when(transportCommunRepository.findById(giventransportCommunId)).thenReturn(Optional.of(transportCommun));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(transportCommunValidators).validate(any(TransportCommun.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateTransportCommun(invalidtransportCommunDto, giventransportCommunId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



     //****     Test Méthod Delete       *******




    @Test
    void shouldDeletetransportCommunById() {
        Long transportCommunId = 1L;
        TransportCommun transportCommun = TransportCommun.builder().id(1L).name("LocationKia").numTel(98546213).build();
        TransportCommunDto transportCommunDto = TransportCommunDto.builder().id(1L).name("LocationKia").numTel(98546213).build();
        when(transportCommunRepository.findById(transportCommunDto.getId())).thenReturn(Optional.of(transportCommun));
        underTest.deleteTransportCommun(transportCommunId);

        verify(transportCommunRepository,times(1)).findById(transportCommunId);
        verify(transportCommunRepository,times(1)).deleteById(transportCommunId);

    }

    @Test
    void shouldNotDeletetransportCommunIfNotFound() {
        Long transportCommunId = 1L;

        when(transportCommunRepository.findById(transportCommunId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteTransportCommun(transportCommunId)).isInstanceOf(EntityNotFoundException.class).hasMessage("transportCommun with this id Not Found");
    }

}

