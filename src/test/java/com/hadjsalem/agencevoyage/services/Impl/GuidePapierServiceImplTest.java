package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.GuidePapierMapper;
import com.hadjsalem.agencevoyage.repositories.GuidePapierRepository;
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
class GuidePapierServiceImplTest {

    @Mock
    private GuidePapierRepository guidePapierRepository;
    
    @Mock
    private GuidePapierMapper guidePapierMapper;
    
    @InjectMocks
    private GuidePapierServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<GuidePapier> guidePapierValidators;
    @Test
    void shouldSaveNewGuidePapier(){
        GuidePapierDto guidePapierDto = GuidePapierDto.builder().libelle("guidePapier1").build();
        GuidePapier guidePapier= GuidePapier.builder().libelle("guidePapier1").build();
        GuidePapier savedGuidePapier= GuidePapier.builder().id(1L).libelle("guidePapier1").build();
        GuidePapierDto expectedGuidePapier= GuidePapierDto.builder().id(1L).libelle("guidePapier1").build();

        when(guidePapierMapper.fromGuidePapierDto(guidePapierDto)).thenReturn(guidePapier);
        when(guidePapierRepository.save(guidePapier)).thenReturn(savedGuidePapier);
        when(guidePapierMapper.fromGuidePapier(savedGuidePapier)).thenReturn(expectedGuidePapier);
        GuidePapierDto result = underTest.saveGuidePapier(guidePapierDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedGuidePapier);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenGuidePapierExists() {
        GuidePapierDto guidePapierDto = GuidePapierDto.builder()
                .libelle("guidePapier1").build();

        GuidePapier guidePapier = GuidePapier.builder()
                .libelle("guidePapier1").build();

        when(guidePapierMapper.fromGuidePapierDto(guidePapierDto)).thenReturn(guidePapier);
        when(guidePapierRepository.existsByLibelle(guidePapierDto.getLibelle())).thenReturn(true);
        Mockito.doNothing().when(guidePapierValidators).validate(Mockito.any(GuidePapier.class));

        assertThatThrownBy(() -> underTest.saveGuidePapier(guidePapierDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un Guide Papier est existe avec Cette Libelle");

        verify(guidePapierRepository, times(1)).existsByLibelle(guidePapierDto.getLibelle());
        verify(guidePapierRepository, never()).save(any(GuidePapier.class));
    }
    @Test
    void ShouldGetAllGuidePapiers() {
        GuidePapier GuidePapier1 = new GuidePapier();
        GuidePapier1.setId(1L);
        GuidePapier GuidePapier2 = new GuidePapier();
        GuidePapier2.setId(2L);
        List<GuidePapier> GuidePapiers = Arrays.asList(GuidePapier1, GuidePapier2);
        Page<GuidePapier> pageGuidePapiers = new PageImpl<>(GuidePapiers);

        GuidePapierDto GuidePapierDto1 = new GuidePapierDto();
        GuidePapierDto1.setId(1L);
        GuidePapierDto GuidePapierDto2 = new GuidePapierDto();
        GuidePapierDto2.setId(2L);

        when(guidePapierRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageGuidePapiers);
        when(guidePapierMapper.fromGuidePapier(GuidePapier1)).thenReturn(GuidePapierDto1);
        when(guidePapierMapper.fromGuidePapier(GuidePapier2)).thenReturn(GuidePapierDto2);

        // When
        PageResponse<GuidePapierDto> result = underTest.getGuidePapiers(0, 2);

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
    void ShouldFindGuidePapierById() {
        Long givenGuidePapierId = 1L;
        GuidePapier guidePapier = GuidePapier.builder().id(1L).libelle("guidePapier1").build();
        GuidePapierDto expected = GuidePapierDto.builder().id(1L).libelle("guidePapier1").build();
        when(guidePapierRepository.findById(givenGuidePapierId)).thenReturn(Optional.of(guidePapier));
        when(guidePapierMapper.fromGuidePapier(guidePapier)).thenReturn(expected);
        GuidePapierDto result = underTest.findGuidePapierById(givenGuidePapierId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindGuidePapierById() {
        Long givenGuidePapierById = 5l;
        when(guidePapierRepository.findById(givenGuidePapierById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findGuidePapierById(givenGuidePapierById)).isInstanceOf(EntityNotFoundException.class).hasMessage("GuidePapier Not Found");

    }

    /*******                Test Method FindGuidePapierByNumTel                    ********* */


    @Test
    void ShouldfindGuidePapierByLibelle() {
        String givenLibelle = "guidePapier1";
        GuidePapier guidePapier = GuidePapier.builder().libelle("guidePapier1").build();
        GuidePapierDto expected = GuidePapierDto.builder().libelle("guidePapier1").build();

        when(guidePapierRepository.findGuidePapierByLibelle(givenLibelle)).thenReturn(Optional.of(guidePapier));
        when(guidePapierMapper.fromGuidePapier(guidePapier)).thenReturn(expected);

        GuidePapierDto result = underTest.findGuidePapierByLibelle(givenLibelle);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindGuidePapierByVille() {
        String  givenLibelle= "guidePapier1";

        when(guidePapierRepository.findGuidePapierByLibelle(givenLibelle)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findGuidePapierByLibelle(givenLibelle)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updateGuidePapier ****/



    @Test
    void ShouldUpdateGuidePapier() {
        Long givenGuidePapierId = 2L;
        GuidePapier guidePapier = GuidePapier.builder().id(2L).libelle("guidePapier1").build();
        GuidePapierDto guidePapierDto = GuidePapierDto.builder().id(2L).libelle("guidePapier1").build();
        GuidePapier updatedGuidePapier = GuidePapier.builder().id(2L).libelle("guidePapier1").build();
        GuidePapierDto expected = GuidePapierDto.builder().id(2L).libelle("guidePapier1").build();

        when(guidePapierRepository.findById(givenGuidePapierId)).thenReturn(Optional.of(guidePapier));
        when(guidePapierRepository.saveAndFlush(guidePapier)).thenReturn(updatedGuidePapier);
        when(guidePapierMapper.fromGuidePapier(updatedGuidePapier)).thenReturn(guidePapierDto);

        GuidePapierDto result = underTest.updateGuidePapier(guidePapierDto, givenGuidePapierId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateGuidePapier() {
        Long givenGuidePapierId = 2L;
        GuidePapier guidePapier = GuidePapier.builder().id(2L).libelle("guidePapier1").build();
        GuidePapierDto guidePapierDto = GuidePapierDto.builder().id(2L).libelle("guidePapier1").build();
        GuidePapier updatedGuidePapier = GuidePapier.builder().id(2L).libelle("guidePapier1").build();
        GuidePapierDto expected = GuidePapierDto.builder().id(2L).libelle("guidePapier1").build();

        when(guidePapierRepository.findById(givenGuidePapierId)).thenReturn(Optional.of(guidePapier));
        when(guidePapierRepository.saveAndFlush(guidePapier)).thenReturn(updatedGuidePapier);
        when(guidePapierMapper.fromGuidePapier(updatedGuidePapier)).thenReturn(guidePapierDto);

        GuidePapierDto result = underTest.updateGuidePapier(guidePapierDto, givenGuidePapierId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenGuidePapierNotFound() {
        Long givenGuidePapierId = 2L;
        GuidePapierDto guidePapierDto = GuidePapierDto.builder()
                .id(2L)
                .libelle("guidePapier1").build();

        when(guidePapierRepository.findById(givenGuidePapierId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateGuidePapier(guidePapierDto, givenGuidePapierId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenGuidePapierId = 2L;
        GuidePapier guidePapier = GuidePapier.builder()
                .id(2L)
                .libelle("guidePapier1").build();
        GuidePapierDto guidePapierDto = GuidePapierDto.builder()
                .id(2L)
                .libelle("guidePapier1").build();

        when(guidePapierRepository.findById(givenGuidePapierId)).thenReturn(Optional.of(guidePapier));
        when(guidePapierRepository.saveAndFlush(guidePapier)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateGuidePapier(guidePapierDto, givenGuidePapierId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenGuidePapierDtoIsInvalid() {
        Long givenGuidePapierId = 2L;
        GuidePapier guidePapier = GuidePapier.builder()
                .id(2L)
                .libelle("guidePapier1").build();
        GuidePapierDto invalidGuidePapierDto = GuidePapierDto.builder()
                .id(2L)
                .libelle("guidePapier1").build();

        when(guidePapierRepository.findById(givenGuidePapierId)).thenReturn(Optional.of(guidePapier));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(guidePapierValidators).validate(any(GuidePapier.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateGuidePapier(invalidGuidePapierDto, givenGuidePapierId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteGuidePapierById() {
        Long GuidePapierId = 1L;
        GuidePapier guidePapier = GuidePapier.builder().id(1L).libelle("guidePapier1").build();
        GuidePapierDto guidePapierDto = GuidePapierDto.builder().id(1L).libelle("guidePapier1").build();
        when(guidePapierRepository.findById(guidePapierDto.getId())).thenReturn(Optional.of(guidePapier));
        underTest.deleteGuidePapier(GuidePapierId);

        verify(guidePapierRepository,times(1)).findById(GuidePapierId);
        verify(guidePapierRepository,times(1)).deleteById(GuidePapierId);

    }

    @Test
    void shouldNotDeleteGuidePapierIfNotFound() {
        Long GuidePapierId = 1L;

        // Simuler que le GuidePapier n'existe pas en faisant en sorte que l'Optional soit vide
        when(guidePapierRepository.findById(GuidePapierId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteGuidePapier(GuidePapierId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Guide Papier with this id Not Found");
    }

}
    
    
    

