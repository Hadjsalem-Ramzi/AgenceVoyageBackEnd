package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;
import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.SocieteLocationMapper;
import com.hadjsalem.agencevoyage.repositories.SocieteLocationRepository;
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
class SocieteLocationServiceImplTest {

    @Mock
    private SocieteLocationMapper societeLocationMapper;
    
    @Mock
    private SocieteLocationRepository societeLocationRepository;
    
    @InjectMocks
    private SocieteLocationServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<SocieteLocation> societeLocationValidators;

    @Test
    void shouldSaveNewsocieteLocation(){
        SocieteLocationDto societeLocationDto = SocieteLocationDto.builder().name("LocationKia").numTel(98546213).build();
        SocieteLocation societeLocation=  SocieteLocation.builder().name("LocationKia").numTel(98546213).build();
        SocieteLocation savedsocieteLocation=  societeLocation.builder().id(1L).name("LocationKia").numTel(98546213).build();
        SocieteLocationDto expectedsocieteLocation= SocieteLocationDto.builder().id(1L).name("LocationKia").numTel(98546213).build();

        when(societeLocationMapper.fromSocieteLocationDto(societeLocationDto)).thenReturn(societeLocation);
        when(societeLocationRepository.save(societeLocation)).thenReturn(savedsocieteLocation);
        when(societeLocationMapper.fromSocieteLocation(savedsocieteLocation)).thenReturn(expectedsocieteLocation);
        SocieteLocationDto result = underTest.saveSocieteLocation(societeLocationDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedsocieteLocation);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhensocieteLocationExists() {
        SocieteLocationDto societeLocationDto = SocieteLocationDto.builder().name("LocationKia").numTel(98546213).build();

        SocieteLocation societeLocation = SocieteLocation.builder().name("LocationKia").numTel(98546213).build();

        when(societeLocationMapper.fromSocieteLocationDto(societeLocationDto)).thenReturn(societeLocation);
        when(societeLocationRepository.existsByNumTel(societeLocationDto.getNumTel())).thenReturn(true);
        Mockito.doNothing().when(societeLocationValidators).validate(Mockito.any(SocieteLocation.class));

        assertThatThrownBy(() -> underTest.saveSocieteLocation(societeLocationDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un societeLocation est existe avec ce nom");

        verify(societeLocationRepository, times(1)).existsByNumTel(societeLocation.getNumTel());
        verify(societeLocationRepository, never()).save(any(SocieteLocation.class));
    }
    @Test
    void ShouldGetAllsocieteLocations() {
        SocieteLocation societeLocation1 = new SocieteLocation();
        societeLocation1.setId(1L);
        SocieteLocation societeLocation2 = new SocieteLocation();
        societeLocation2.setId(2L);
        List<SocieteLocation> societeLocations = Arrays.asList(societeLocation1, societeLocation2);
        Page<SocieteLocation> pagesocieteLocations = new PageImpl<>(societeLocations);

        SocieteLocationDto societeLocationDto1 = new SocieteLocationDto();
        societeLocationDto1.setId(1L);
        SocieteLocationDto societeLocationDto2 = new SocieteLocationDto();
        societeLocationDto2.setId(2L);

        when(societeLocationRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pagesocieteLocations);
        when(societeLocationMapper.fromSocieteLocation(societeLocation1)).thenReturn(societeLocationDto1);
        when(societeLocationMapper.fromSocieteLocation(societeLocation2)).thenReturn(societeLocationDto2);

        // When
        PageResponse<SocieteLocationDto> result = underTest.getSocieteLocations(0, 2);

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
    void ShouldFindsocieteLocationById() {
        Long givensocieteLocationId = 1L;
        SocieteLocation societeLocation = SocieteLocation.builder().id(1L).name("LocationKia").numTel(98546213).build();
        SocieteLocationDto expected = SocieteLocationDto.builder().id(1L).name("LocationKia").numTel(98546213).build();
        when(societeLocationRepository.findById(givensocieteLocationId)).thenReturn(Optional.of(societeLocation));
        when(societeLocationMapper.fromSocieteLocation(societeLocation)).thenReturn(expected);
        SocieteLocationDto result = underTest.findSocieteLocationById(givensocieteLocationId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindsocieteLocationById() {
        Long givensocieteLocationById = 5l;
        when(societeLocationRepository.findById(givensocieteLocationById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findSocieteLocationById(givensocieteLocationById)).isInstanceOf(EntityNotFoundException.class).hasMessage("societeLocation Not Found");

    }

    /*******                Test Method FindsocieteLocationByNumTel                    ********* */


    @Test
    void ShouldfindsocieteLocationByNom() {
       String givenSocieteLocation = "LocationKia";
        SocieteLocation societeLocation = SocieteLocation.builder().name("LocationKia").numTel(98546213).build();
        SocieteLocationDto expected = SocieteLocationDto.builder().name("LocationKia").numTel(98546213).build();

        when(societeLocationRepository.findSocieteLocationByNom(givenSocieteLocation)).thenReturn(Optional.of(societeLocation));
        when(societeLocationMapper.fromSocieteLocation(societeLocation)).thenReturn(expected);

        SocieteLocationDto result = underTest.findSocieteLocationByName(societeLocation.getName());

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindsocieteLocationByNom() {
        String givenSocieteLocation = "LocationKia";
        when(societeLocationRepository.findSocieteLocationByNom(givenSocieteLocation)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findSocieteLocationByName(givenSocieteLocation)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updatesocieteLocation ****/



    @Test
    void ShouldUpdatesocieteLocation() {
        Long givensocieteLocationId = 2L;
        SocieteLocation societeLocation = SocieteLocation.builder().id(2L).name("LocationKia").numTel(98546213).build();
        SocieteLocationDto societeLocationDto = SocieteLocationDto.builder().id(2L).name("LocationKia").numTel(98546213).build();
        SocieteLocation updatedsocieteLocation = societeLocation.builder().id(2L).name("LocationKia").numTel(98546213).build();
        SocieteLocationDto expected = SocieteLocationDto.builder().id(2L).name("LocationKia").numTel(98546213).build();

        when(societeLocationRepository.findById(givensocieteLocationId)).thenReturn(Optional.of(societeLocation));
        when(societeLocationRepository.saveAndFlush(societeLocation)).thenReturn(updatedsocieteLocation);
        when(societeLocationMapper.fromSocieteLocation(updatedsocieteLocation)).thenReturn(societeLocationDto);


        SocieteLocationDto result = underTest.updateSocieteLocation(societeLocationDto, givensocieteLocationId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdatesocieteLocation() {
        Long givensocieteLocationId = 2L;
        SocieteLocation societeLocation = SocieteLocation.builder().id(2L).name("LocationKia").numTel(98546213).build();
        SocieteLocationDto societeLocationDto = SocieteLocationDto.builder().id(2L).name("LocationKia").numTel(98546213).build();
        SocieteLocation updatedsocieteLocation = societeLocation.builder().id(2L).name("LocationKia").numTel(98546213).build();
        SocieteLocationDto expected = SocieteLocationDto.builder().id(2L).name("LocationKia").numTel(98546213).build();

        when(societeLocationRepository.findById(givensocieteLocationId)).thenReturn(Optional.of(societeLocation));
        when(societeLocationRepository.saveAndFlush(societeLocation)).thenReturn(updatedsocieteLocation);
        when(societeLocationMapper.fromSocieteLocation(updatedsocieteLocation)).thenReturn(societeLocationDto);

        SocieteLocationDto result = underTest.updateSocieteLocation(societeLocationDto, givensocieteLocationId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhensocieteLocationNotFound() {
        Long givensocieteLocationId = 2L;
        SocieteLocationDto societeLocationDto = SocieteLocationDto.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();

        when(societeLocationRepository.findById(givensocieteLocationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateSocieteLocation(societeLocationDto, givensocieteLocationId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givensocieteLocationId = 2L;
        SocieteLocation societeLocation = SocieteLocation.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();
        SocieteLocationDto societeLocationDto = SocieteLocationDto.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();

        when(societeLocationRepository.findById(givensocieteLocationId)).thenReturn(Optional.of(societeLocation));
        when(societeLocationRepository.saveAndFlush(societeLocation)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateSocieteLocation(societeLocationDto, givensocieteLocationId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenSocieteLocationDtoIsInvalid() {
        Long givensocieteLocationId = 2L;
        SocieteLocation societeLocation = SocieteLocation.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();
        SocieteLocationDto invalidSocieteLocationDto = SocieteLocationDto.builder()
                .id(2L)
                .name("LocationKia").numTel(98546213).build();

        when(societeLocationRepository.findById(givensocieteLocationId)).thenReturn(Optional.of(societeLocation));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(societeLocationValidators).validate(any(SocieteLocation.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateSocieteLocation(invalidSocieteLocationDto, givensocieteLocationId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeletesocieteLocationById() {
        Long societeLocationId = 1L;
        SocieteLocation societeLocation = SocieteLocation.builder().id(1L).name("LocationKia").numTel(98546213).build();
        SocieteLocationDto societeLocationDto = SocieteLocationDto.builder().id(1L).name("LocationKia").numTel(98546213).build();
        when(societeLocationRepository.findById(societeLocationDto.getId())).thenReturn(Optional.of(societeLocation));
        underTest.deleteSocieteLocation(societeLocationId);

        verify(societeLocationRepository,times(1)).findById(societeLocationId);
        verify(societeLocationRepository,times(1)).deleteById(societeLocationId);

    }

    @Test
    void shouldNotDeletesocieteLocationIfNotFound() {
        Long societeLocationId = 1L;

        when(societeLocationRepository.findById(societeLocationId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteSocieteLocation(societeLocationId)).isInstanceOf(EntityNotFoundException.class).hasMessage("societeLocation with this id Not Found");
    }

}

