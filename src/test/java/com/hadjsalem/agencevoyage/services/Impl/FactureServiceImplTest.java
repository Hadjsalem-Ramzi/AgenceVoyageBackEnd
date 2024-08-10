package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.FactureDto;
import com.hadjsalem.agencevoyage.entities.Facture;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.FactureMapper;
import com.hadjsalem.agencevoyage.repositories.FactureRepository;
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
class FactureServiceImplTest  {
    
    @Mock
    private FactureRepository factureRepository;
    
    @Mock
    private FactureMapper factureMapper;
    
    @InjectMocks
    private FactureServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<Facture> factureValidators;
    @Test
    void shouldSaveNewFacture(){
        FactureDto factureDto = FactureDto.builder().designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        Facture facture= Facture.builder().designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        Facture savedFacture= Facture.builder().id(1L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto expectedFacture= factureDto.builder().id(1L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureMapper.fromFactureDto(factureDto)).thenReturn(facture);
        when(factureRepository.save(facture)).thenReturn(savedFacture);
        when(factureMapper.fromFacture(savedFacture)).thenReturn(expectedFacture);
        FactureDto result = underTest.saveFacture(factureDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedFacture);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenFactureExists() {
        FactureDto factureDto = FactureDto.builder().designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        Facture facture = Facture.builder()
                .designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureMapper.fromFactureDto(factureDto)).thenReturn(facture);
        when(factureRepository.existsByDesignation(factureDto.getDesignation())).thenReturn(true);
        Mockito.doNothing().when(factureValidators).validate(Mockito.any(Facture.class));

        assertThatThrownBy(() -> underTest.saveFacture(factureDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("une Facture est existe avec Cette designation");

        verify(factureRepository, times(1)).existsByDesignation(factureDto.getDesignation());
        verify(factureRepository, never()).save(any(Facture.class));
    }
    @Test
    void ShouldGetAllFactures() {
        Facture Facture1 = new Facture();
        Facture1.setId(1L);
        Facture Facture2 = new Facture();
        Facture2.setId(2L);
        List<Facture> Factures = Arrays.asList(Facture1, Facture2);
        Page<Facture> pageFactures = new PageImpl<>(Factures);

        FactureDto factureDto1 = new FactureDto();
        factureDto1.setId(1L);
        FactureDto factureDto2 = new FactureDto();
        factureDto2.setId(2L);

        when(factureRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageFactures);
        when(factureMapper.fromFacture(Facture1)).thenReturn(factureDto1);
        when(factureMapper.fromFacture(Facture2)).thenReturn(factureDto2);

        // When
        PageResponse<FactureDto> result = underTest.getFactures(0, 2);

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
    void ShouldFindFactureById() {
        Long givenFactureId = 1L;
        Facture facture = Facture.builder().id(1L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto expected = FactureDto.builder().id(1L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        when(factureRepository.findById(givenFactureId)).thenReturn(Optional.of(facture));
        when(factureMapper.fromFacture(facture)).thenReturn(expected);
        FactureDto result = underTest.findFactureById(givenFactureId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindFactureById() {
        Long givenFactureById = 5l;
        when(factureRepository.findById(givenFactureById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findFactureById(givenFactureById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Facture Not Found");

    }

    /*                Test Method FindFactureByNumTel                    **********/


    @Test
    void ShouldfindFactureByDesignation() {
        String givenDesignation = "facture1";
        Facture facture = Facture.builder().designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto expected = FactureDto.builder().designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureRepository.findFactureByDesignation(givenDesignation)).thenReturn(Optional.of(facture));
        when(factureMapper.fromFacture(facture)).thenReturn(expected);

        FactureDto result = underTest.findFactureByDesignation(givenDesignation);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindFactureByDesignation() {
        String  givenDesignation= "facture1";

        when(factureRepository.findFactureByDesignation(givenDesignation)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findFactureByDesignation(givenDesignation)).isInstanceOf(EntityNotFoundException.class);
    }


    /*    Test  Method updateFacture ****/



    @Test
    void ShouldUpdateFacture() {
        Long givenFactureId = 2L;
        Facture facture = Facture.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto factureDto = FactureDto.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        Facture updatedFacture = Facture.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto expected = factureDto.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureRepository.findById(givenFactureId)).thenReturn(Optional.of(facture));
        when(factureRepository.saveAndFlush(facture)).thenReturn(updatedFacture);
        when(factureMapper.fromFacture(updatedFacture)).thenReturn(factureDto);

        FactureDto result = underTest.updateFacture(factureDto, givenFactureId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateFacture() {
        Long givenFactureId = 2L;
        Facture facture = Facture.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto factureDto = FactureDto.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        Facture updatedFacture = Facture.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto expected = factureDto.builder().id(2L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureRepository.findById(givenFactureId)).thenReturn(Optional.of(facture));
        when(factureRepository.saveAndFlush(facture)).thenReturn(updatedFacture);
        when(factureMapper.fromFacture(updatedFacture)).thenReturn(factureDto);

        FactureDto result = underTest.updateFacture(factureDto, givenFactureId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenFactureNotFound() {
        Long givenFactureId = 2L;
        FactureDto factureDto = FactureDto.builder()
                .id(2L)
                .designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureRepository.findById(givenFactureId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateFacture(factureDto, givenFactureId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenFactureId = 2L;
        Facture facture = Facture.builder()
                .id(2L)
                .designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto factureDto = FactureDto.builder()
                .id(2L)
                .designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureRepository.findById(givenFactureId)).thenReturn(Optional.of(facture));
        when(factureRepository.saveAndFlush(facture)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateFacture(factureDto, givenFactureId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenfactureDtoIsInvalid() {
        Long givenFactureId = 2L;
        Facture facture = Facture.builder()
                .id(2L)
                .designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto invalidfactureDto = FactureDto.builder()
                .id(2L)
                .designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();

        when(factureRepository.findById(givenFactureId)).thenReturn(Optional.of(facture));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(factureValidators).validate(any(Facture.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateFacture(invalidfactureDto, givenFactureId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteFactureById() {
        Long FactureId = 1L;
        Facture facture = Facture.builder().id(1L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        FactureDto factureDto = FactureDto.builder().id(1L).designation("facture1").quantite(100L).prixUnitaire(120.50).prixTotal(10000.50).Total(99999.00).build();
        when(factureRepository.findById(factureDto.getId())).thenReturn(Optional.of(facture));
        underTest.deleteFacture(FactureId);

        verify(factureRepository,times(1)).findById(FactureId);
        verify(factureRepository,times(1)).deleteById(FactureId);

    }

    @Test
    void shouldNotDeleteFactureIfNotFound() {
        Long FactureId = 1L;

        // Simuler que le Facture n'existe pas en faisant en sorte que l'Optional soit vide
        when(factureRepository.findById(FactureId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteFacture(FactureId)).isInstanceOf(EntityNotFoundException.class).hasMessage("f acture with this id Not Found");
    }

}
    
    
    

