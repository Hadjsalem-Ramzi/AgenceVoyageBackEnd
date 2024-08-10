package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.MoyenTransportMapper;
import com.hadjsalem.agencevoyage.repositories.MoyenTransportRepository;
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
class MoyenTransportServiceImplTest {

    @Mock
    private MoyenTransportMapper moyenTransportMapper;
    
    @Mock
    private MoyenTransportRepository moyenTransportRepository;
    
    @InjectMocks
    private MoyenTransportServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<MoyenTransport>  moyenTransportValidators;

    @Test
    void shouldSaveNewMoyenTransport(){
        MoyenTransportDto moyenTransportDto = MoyenTransportDto.builder().nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransport moyenTransport= MoyenTransport.builder().nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransport savedMoyenTransport= MoyenTransport.builder().id(1L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto expectedMoyenTransport= MoyenTransportDto.builder().id(1L).nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportMapper.fromMoyenTransportDto(moyenTransportDto)).thenReturn(moyenTransport);
        when(moyenTransportRepository.save(moyenTransport)).thenReturn(savedMoyenTransport);
        when(moyenTransportMapper.fromMoyenTransport(savedMoyenTransport)).thenReturn(expectedMoyenTransport);
        MoyenTransportDto result = underTest.saveMoyenTransport(moyenTransportDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedMoyenTransport);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenMoyenTransportExists() {
        MoyenTransportDto moyenTransportDto = MoyenTransportDto.builder()
                .nom("BusAZ").capacite(150L).Type("Terrestre").build();

        MoyenTransport moyenTransport = MoyenTransport.builder()
                .nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportMapper.fromMoyenTransportDto(moyenTransportDto)).thenReturn(moyenTransport);
        when(moyenTransportRepository.existsByNom(moyenTransportDto.getNom())).thenReturn(true);
        Mockito.doNothing().when(moyenTransportValidators).validate(Mockito.any(MoyenTransport.class));

        assertThatThrownBy(() -> underTest.saveMoyenTransport(moyenTransportDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("moyen Transport with this Nom was Found");

        verify(moyenTransportRepository, times(1)).existsByNom(moyenTransportDto.getNom());
        verify(moyenTransportRepository, never()).save(any(MoyenTransport.class));
    }
    @Test
    void ShouldGetAllMoyenTransports() {
        MoyenTransport MoyenTransport1 = new MoyenTransport();
        MoyenTransport1.setId(1L);
        MoyenTransport MoyenTransport2 = new MoyenTransport();
        MoyenTransport2.setId(2L);
        List<MoyenTransport> MoyenTransports = Arrays.asList(MoyenTransport1, MoyenTransport2);
        Page<MoyenTransport> pageMoyenTransports = new PageImpl<>(MoyenTransports);

        MoyenTransportDto MoyenTransportDto1 = new MoyenTransportDto();
        MoyenTransportDto1.setId(1L);
        MoyenTransportDto MoyenTransportDto2 = new MoyenTransportDto();
        MoyenTransportDto2.setId(2L);

        when(moyenTransportRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageMoyenTransports);
        when(moyenTransportMapper.fromMoyenTransport(MoyenTransport1)).thenReturn(MoyenTransportDto1);
        when(moyenTransportMapper.fromMoyenTransport(MoyenTransport2)).thenReturn(MoyenTransportDto2);

        // When
        PageResponse<MoyenTransportDto> result = underTest.getMoyenTransports(0, 2);

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
    /****         Test GetById Method                */


    @Test
    void ShouldFindMoyenTransportById() {
        Long givenMoyenTransportId = 1L;
        MoyenTransport moyenTransport = MoyenTransport.builder().id(1L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto expected = MoyenTransportDto.builder().id(1L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        when(moyenTransportRepository.findById(givenMoyenTransportId)).thenReturn(Optional.of(moyenTransport));
        when(moyenTransportMapper.fromMoyenTransport(moyenTransport)).thenReturn(expected);
        MoyenTransportDto result = underTest.findMoyenTransportById(givenMoyenTransportId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindMoyenTransportById() {
        Long givenMoyenTransportById = 5l;
        when(moyenTransportRepository.findById(givenMoyenTransportById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findMoyenTransportById(givenMoyenTransportById)).isInstanceOf(EntityNotFoundException.class).hasMessage("MoyenTransport Not Found");

    }

    /*******                Test Method FindMoyenTransportByNumTel                    ********* */


    @Test
    void ShouldfindMoyenTransportByLibelle() {
        String givenLibelle = "Hannibaal";
        MoyenTransport moyenTransport = MoyenTransport.builder().nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto expected = MoyenTransportDto.builder().nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportRepository.findMoyenTransportByNom(givenLibelle)).thenReturn(Optional.of(moyenTransport));
        when(moyenTransportMapper.fromMoyenTransport(moyenTransport)).thenReturn(expected);

        MoyenTransportDto result = underTest.findMoyenTransportByNom(givenLibelle);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindMoyenTransportByLibelle() {
        String  givenLibelle= "hannibaal";

        when(moyenTransportRepository.findMoyenTransportByNom(givenLibelle)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findMoyenTransportByNom(givenLibelle)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updateMoyenTransport ****/



    @Test
    void ShouldUpdateMoyenTransport() {
        Long givenMoyenTransportId = 2L;
        MoyenTransport moyenTransport = MoyenTransport.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto moyenTransportDto = MoyenTransportDto.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransport updatedMoyenTransport = MoyenTransport.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto expected = MoyenTransportDto.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportRepository.findById(givenMoyenTransportId)).thenReturn(Optional.of(moyenTransport));
        when(moyenTransportRepository.saveAndFlush(moyenTransport)).thenReturn(updatedMoyenTransport);
        when(moyenTransportMapper.fromMoyenTransport(updatedMoyenTransport)).thenReturn(moyenTransportDto);

        MoyenTransportDto result = underTest.updateMoyenTransport(moyenTransportDto, givenMoyenTransportId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateMoyenTransport() {
        Long givenMoyenTransportId = 2L;
        MoyenTransport moyenTransport = MoyenTransport.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto moyenTransportDto = MoyenTransportDto.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransport updatedMoyenTransport = MoyenTransport.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto expected = MoyenTransportDto.builder().id(2L).nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportRepository.findById(givenMoyenTransportId)).thenReturn(Optional.of(moyenTransport));
        when(moyenTransportRepository.saveAndFlush(moyenTransport)).thenReturn(updatedMoyenTransport);
        when(moyenTransportMapper.fromMoyenTransport(updatedMoyenTransport)).thenReturn(moyenTransportDto);

        MoyenTransportDto result = underTest.updateMoyenTransport(moyenTransportDto, givenMoyenTransportId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenMoyenTransportNotFound() {
        Long givenMoyenTransportId = 2L;
        MoyenTransportDto moyenTransportDto = MoyenTransportDto.builder()
                .id(2L)
                .nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportRepository.findById(givenMoyenTransportId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateMoyenTransport(moyenTransportDto, givenMoyenTransportId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenMoyenTransportId = 2L;
        MoyenTransport moyenTransport = MoyenTransport.builder()
                .id(2L)
                .nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto moyenTransportDto = MoyenTransportDto.builder()
                .id(2L)
                .nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportRepository.findById(givenMoyenTransportId)).thenReturn(Optional.of(moyenTransport));
        when(moyenTransportRepository.saveAndFlush(moyenTransport)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateMoyenTransport(moyenTransportDto, givenMoyenTransportId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenMoyenTransportDtoIsInvalid() {
        Long givenMoyenTransportId = 2L;
        MoyenTransport moyenTransport = MoyenTransport.builder()
                .id(2L)
                .nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto invalidMoyenTransportDto = MoyenTransportDto.builder()
                .id(2L)
                .nom("BusAZ").capacite(150L).Type("Terrestre").build();

        when(moyenTransportRepository.findById(givenMoyenTransportId)).thenReturn(Optional.of(moyenTransport));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(moyenTransportValidators).validate(any(MoyenTransport.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateMoyenTransport(invalidMoyenTransportDto, givenMoyenTransportId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteMoyenTransportById() {
        Long MoyenTransportId = 1L;
        MoyenTransport moyenTransport = MoyenTransport.builder().id(1L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        MoyenTransportDto moyenTransportDto = MoyenTransportDto.builder().id(1L).nom("BusAZ").capacite(150L).Type("Terrestre").build();
        when(moyenTransportRepository.findById(moyenTransportDto.getId())).thenReturn(Optional.of(moyenTransport));
        underTest.deleteMoyenTransport(MoyenTransportId);

        verify(moyenTransportRepository,times(1)).findById(MoyenTransportId);
        verify(moyenTransportRepository,times(1)).deleteById(MoyenTransportId);

    }

    @Test
    void shouldNotDeleteMoyenTransportIfNotFound() {
        Long MoyenTransportId = 1L;

        when(moyenTransportRepository.findById(MoyenTransportId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteMoyenTransport(MoyenTransportId)).isInstanceOf(EntityNotFoundException.class).hasMessage("moyen Transport with this Nom not Found");
    }

}
