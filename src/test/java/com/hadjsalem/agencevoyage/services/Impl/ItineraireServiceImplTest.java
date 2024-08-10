package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ItineraireDto;
import com.hadjsalem.agencevoyage.entities.Itineraire;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.ItineraireMapper;
import com.hadjsalem.agencevoyage.repositories.ItineraireRepository;
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
class ItineraireServiceImplTest {
    
    @Mock
    private ItineraireMapper itineraireMapper;
    
    @Mock
    private ItineraireRepository itineraireRepository;
    
    @InjectMocks
    private ItineraireServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<Itineraire> itineraireValidators;
    @Test
    void shouldSaveNewItineraire(){
        ItineraireDto itineraireDto = ItineraireDto.builder().libelle("itineraire1").caracteristique("terrestre").build();
        Itineraire itineraire= Itineraire.builder().libelle("itineraire1").caracteristique("terrestre").build();
        Itineraire savedItineraire= Itineraire.builder().id(1L).libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto expectedItineraire= ItineraireDto.builder().id(1L).libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireMapper.fromItineraireDto(itineraireDto)).thenReturn(itineraire);
        when(itineraireRepository.save(itineraire)).thenReturn(savedItineraire);
        when(itineraireMapper.fromItineraire(savedItineraire)).thenReturn(expectedItineraire);
        ItineraireDto result = underTest.saveItineraire(itineraireDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedItineraire);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenItineraireExists() {
        ItineraireDto itineraireDto = ItineraireDto.builder()
                .libelle("itineraire1").caracteristique("terrestre").build();

        Itineraire itineraire = Itineraire.builder()
                .libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireMapper.fromItineraireDto(itineraireDto)).thenReturn(itineraire);
        when(itineraireRepository.existsByLibelle(itineraireDto.getLibelle())).thenReturn(true);
        Mockito.doNothing().when(itineraireValidators).validate(Mockito.any(Itineraire.class));

        assertThatThrownBy(() -> underTest.saveItineraire(itineraireDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("Itineraire was Found with this Libelle");

        verify(itineraireRepository, times(1)).existsByLibelle(itineraireDto.getLibelle());
        verify(itineraireRepository, never()).save(any(Itineraire.class));
    }
    @Test
    void ShouldGetAllItineraires() {
        Itineraire Itineraire1 = new Itineraire();
        Itineraire1.setId(1L);
        Itineraire Itineraire2 = new Itineraire();
        Itineraire2.setId(2L);
        List<Itineraire> Itineraires = Arrays.asList(Itineraire1, Itineraire2);
        Page<Itineraire> pageItineraires = new PageImpl<>(Itineraires);

        ItineraireDto ItineraireDto1 = new ItineraireDto();
        ItineraireDto1.setId(1L);
        ItineraireDto ItineraireDto2 = new ItineraireDto();
        ItineraireDto2.setId(2L);

        when(itineraireRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageItineraires);
        when(itineraireMapper.fromItineraire(Itineraire1)).thenReturn(ItineraireDto1);
        when(itineraireMapper.fromItineraire(Itineraire2)).thenReturn(ItineraireDto2);

        // When
        PageResponse<ItineraireDto> result = underTest.getItineraires(0, 2);

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
    void ShouldFindItineraireById() {
        Long givenItineraireId = 1L;
        Itineraire itineraire = Itineraire.builder().id(1L).libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto expected = ItineraireDto.builder().id(1L).libelle("itineraire1").caracteristique("terrestre").build();
        when(itineraireRepository.findById(givenItineraireId)).thenReturn(Optional.of(itineraire));
        when(itineraireMapper.fromItineraire(itineraire)).thenReturn(expected);
        ItineraireDto result = underTest.findItineraireById(givenItineraireId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindItineraireById() {
        Long givenItineraireById = 5l;
        when(itineraireRepository.findById(givenItineraireById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findItineraireById(givenItineraireById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Itineraire Not Found");

    }

    /*******                Test Method FindItineraireByNumTel                    ********* */


    @Test
    void ShouldfindItineraireByLibelle() {
        String givenLibelle = "Hannibaal";
        Itineraire itineraire = Itineraire.builder().libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto expected = ItineraireDto.builder().libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireRepository.findItineraireByLibelle(givenLibelle)).thenReturn(Optional.of(itineraire));
        when(itineraireMapper.fromItineraire(itineraire)).thenReturn(expected);

        ItineraireDto result = underTest.findItineraireByLibelle(givenLibelle);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindItineraireByLibelle() {
        String  givenLibelle= "hannibaal";

        when(itineraireRepository.findItineraireByLibelle(givenLibelle)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findItineraireByLibelle(givenLibelle)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updateItineraire ****/



    @Test
    void ShouldUpdateItineraire() {
        Long givenItineraireId = 2L;
        Itineraire itineraire = Itineraire.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto itineraireDto = ItineraireDto.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();
        Itineraire updatedItineraire = Itineraire.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto expected = ItineraireDto.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireRepository.findById(givenItineraireId)).thenReturn(Optional.of(itineraire));
        when(itineraireRepository.saveAndFlush(itineraire)).thenReturn(updatedItineraire);
        when(itineraireMapper.fromItineraire(updatedItineraire)).thenReturn(itineraireDto);

        ItineraireDto result = underTest.updateItineraire(itineraireDto, givenItineraireId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateItineraire() {
        Long givenItineraireId = 2L;
        Itineraire itineraire = Itineraire.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto itineraireDto = ItineraireDto.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();
        Itineraire updatedItineraire = Itineraire.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto expected = ItineraireDto.builder().id(2L).libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireRepository.findById(givenItineraireId)).thenReturn(Optional.of(itineraire));
        when(itineraireRepository.saveAndFlush(itineraire)).thenReturn(updatedItineraire);
        when(itineraireMapper.fromItineraire(updatedItineraire)).thenReturn(itineraireDto);

        ItineraireDto result = underTest.updateItineraire(itineraireDto, givenItineraireId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenItineraireNotFound() {
        Long givenItineraireId = 2L;
        ItineraireDto itineraireDto = ItineraireDto.builder()
                .id(2L)
                .libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireRepository.findById(givenItineraireId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateItineraire(itineraireDto, givenItineraireId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenItineraireId = 2L;
        Itineraire itineraire = Itineraire.builder()
                .id(2L)
                .libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto itineraireDto = ItineraireDto.builder()
                .id(2L)
                .libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireRepository.findById(givenItineraireId)).thenReturn(Optional.of(itineraire));
        when(itineraireRepository.saveAndFlush(itineraire)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateItineraire(itineraireDto, givenItineraireId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenItineraireDtoIsInvalid() {
        Long givenItineraireId = 2L;
        Itineraire itineraire = Itineraire.builder()
                .id(2L)
                .libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto invalidItineraireDto = ItineraireDto.builder()
                .id(2L)
                .libelle("itineraire1").caracteristique("terrestre").build();

        when(itineraireRepository.findById(givenItineraireId)).thenReturn(Optional.of(itineraire));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(itineraireValidators).validate(any(Itineraire.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateItineraire(invalidItineraireDto, givenItineraireId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteItineraireById() {
        Long ItineraireId = 1L;
        Itineraire itineraire = Itineraire.builder().id(1L).libelle("itineraire1").caracteristique("terrestre").build();
        ItineraireDto itineraireDto = ItineraireDto.builder().id(1L).libelle("itineraire1").caracteristique("terrestre").build();
        when(itineraireRepository.findById(itineraireDto.getId())).thenReturn(Optional.of(itineraire));
        underTest.deleteItineraire(ItineraireId);

        verify(itineraireRepository,times(1)).findById(ItineraireId);
        verify(itineraireRepository,times(1)).deleteById(ItineraireId);

    }

    @Test
    void shouldNotDeleteItineraireIfNotFound() {
        Long ItineraireId = 1L;

        when(itineraireRepository.findById(ItineraireId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteItineraire(ItineraireId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Itineraire with this id Not Found");
    }

}
  
