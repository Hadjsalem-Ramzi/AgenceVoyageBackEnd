/*
package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.CompagnieTransportMapper;
import com.hadjsalem.agencevoyage.repositories.CompagnieTransportRepository;
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
class CompagnieTransportServiceImplTest {

    @Mock
    private CompagnieTransportRepository compagnieTransportRepository;
    @Mock
    private CompagnieTransportMapper compagnieTransportMapper;

    @InjectMocks
    private CompagnieTransportServiceImpl underTest;

    @Mock
    private ObjectsValidators<CompagnieTransport> compagnieTransportValidators;




    @Test
    void shouldSaveNewCompagnieTransport(){
        CompagnieTransportDto compagnieTransportDto=CompagnieTransportDto.builder().nom("Hanibaal").numTel(97852364).build();
        CompagnieTransport compagnieTransport=CompagnieTransport.builder().nom("Hanibaal").numTel(97852364).build();
        CompagnieTransport savedCompagnieTransport= CompagnieTransport.builder().id(1).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto expectedCompagnieTransport= CompagnieTransportDto.builder().id(1L).nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportMapper.fromCompagnieTransportDto(compagnieTransportDto)).thenReturn(compagnieTransport);
        when(compagnieTransportRepository.save(compagnieTransport)).thenReturn(savedCompagnieTransport);
        when(compagnieTransportMapper.fromCompagnieTransport(savedCompagnieTransport)).thenReturn(expectedCompagnieTransport);
        CompagnieTransportDto result = underTest.saveCompagnieTransport(compagnieTransportDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedCompagnieTransport);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenCompagnieTransportExists() {
        CompagnieTransportDto compagnieTransportDto = CompagnieTransportDto.builder()
                .nom("Hanibaal").numTel(97852364).build();

        CompagnieTransport compagnieTransport = CompagnieTransport.builder()
                .nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportMapper.fromCompagnieTransportDto(compagnieTransportDto)).thenReturn(compagnieTransport);
        when(compagnieTransportRepository.existsByNumTel(compagnieTransportDto.getNumTel())).thenReturn(true);
        Mockito.doNothing().when(compagnieTransportValidators).validate(Mockito.any(CompagnieTransport.class));

        assertThatThrownBy(() -> underTest.saveCompagnieTransport(compagnieTransportDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un CompagnieTransport est existe avec cette email");

        verify(compagnieTransportRepository, times(1)).existsByNumTel(compagnieTransportDto.getNumTel());
        verify(compagnieTransportRepository, never()).save(any(CompagnieTransport.class));
    }
    @Test
    void ShouldGetAllCompagnieTransports() {
        CompagnieTransport compagnieTransport1 = new CompagnieTransport();
        compagnieTransport1.setId(1);
        CompagnieTransport compagnieTransport2 = new CompagnieTransport();
        compagnieTransport2.setId(2);
        List<CompagnieTransport> CompagnieTransports = Arrays.asList(compagnieTransport1, compagnieTransport2);
        Page<CompagnieTransport> pageCompagnieTransports = new PageImpl<>(CompagnieTransports);

        CompagnieTransportDto compagnieTransportDto1 = new CompagnieTransportDto();
        compagnieTransportDto1.setId(1L);
        CompagnieTransportDto compagnieTransportDto2 = new CompagnieTransportDto();
        compagnieTransportDto2.setId(2L);

        when(compagnieTransportRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageCompagnieTransports);
        when(compagnieTransportMapper.fromCompagnieTransport(compagnieTransport1)).thenReturn(compagnieTransportDto1);
        when(compagnieTransportMapper.fromCompagnieTransport(compagnieTransport2)).thenReturn(compagnieTransportDto2);

        // When
        PageResponse<CompagnieTransportDto> result = underTest.getCompagnieTransports(0, 2);

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
    */
/******         Test GetById Method                ***//*

    @Test
    void ShouldFindCompagnieTransportById() {
        Long givenCompagnieTransportId = 1L;
        CompagnieTransport compagnieTransport = CompagnieTransport.builder().id(1).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto expected = CompagnieTransportDto.builder().id(1L).nom("Hanibaal").numTel(97852364).build();
        when(compagnieTransportRepository.findById(givenCompagnieTransportId)).thenReturn(Optional.of(compagnieTransport));
        when(compagnieTransportMapper.fromCompagnieTransport(compagnieTransport)).thenReturn(expected);
        CompagnieTransportDto result = underTest.findCompagnieTransportById(givenCompagnieTransportId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindCompagnieTransportById() {
        Long givenCompagnieTransportById = 5l;
        when(compagnieTransportRepository.findById(givenCompagnieTransportById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findCompagnieTransportById(givenCompagnieTransportById)).isInstanceOf(EntityNotFoundException.class).hasMessage("CompagnieTransport Not Found");

    }

    */
/***                Test Method FindCompagnieTransportByNumTel                    ************//*

    @Test
    void ShouldfindCompagnieTransportByNom() {
        String givenNom = "hadjsalemramzi24@gmail.com";
        CompagnieTransport compagnieTransport = CompagnieTransport.builder().nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto expected = CompagnieTransportDto.builder().nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportRepository.findCompagnieTransportByNom(givenNom)).thenReturn(Optional.of(compagnieTransport));
        when(compagnieTransportMapper.fromCompagnieTransport(compagnieTransport)).thenReturn(expected);

        CompagnieTransportDto result = underTest.findCompagnieTransportByNom(givenNom);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindCompagnieTransportByEmail() {
        String  givenNom= "Hanibaal";

        when(compagnieTransportRepository.findCompagnieTransportByNom(givenNom)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findCompagnieTransportByNom(givenNom)).isInstanceOf(EntityNotFoundException.class);
    }

*/
/**********************************************************************************************//*

    */
/***    Test  Method updateCompagnieTransport ******//*


    @Test
    void ShouldUpdateCompagnieTransport() {
        Long givenCompagnieTransportId = 2L;
        CompagnieTransport compagnieTransport = CompagnieTransport.builder().id(2).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto compagnieTransportDto = CompagnieTransportDto.builder().id(2L).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransport updatedCompagnieTransport = CompagnieTransport.builder().id(2).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto expected = CompagnieTransportDto.builder().id(2L).nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportRepository.findById(givenCompagnieTransportId)).thenReturn(Optional.of(compagnieTransport));
        when(compagnieTransportRepository.saveAndFlush(compagnieTransport)).thenReturn(updatedCompagnieTransport);
        when(compagnieTransportMapper.fromCompagnieTransport(updatedCompagnieTransport)).thenReturn(compagnieTransportDto);

        CompagnieTransportDto result = underTest.updateCompagnieTransport(compagnieTransportDto, givenCompagnieTransportId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateCompagnieTransport() {
        Long givenCompagnieTransportId = 2L;
        CompagnieTransport compagnieTransport = CompagnieTransport.builder().id(2).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto compagnieTransportDto = CompagnieTransportDto.builder().id(2L).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransport updatedCompagnieTransport = CompagnieTransport.builder().id(2).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto expected = CompagnieTransportDto.builder().id(2L).nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportRepository.findById(givenCompagnieTransportId)).thenReturn(Optional.of(compagnieTransport));
        when(compagnieTransportRepository.saveAndFlush(compagnieTransport)).thenReturn(updatedCompagnieTransport);
        when(compagnieTransportMapper.fromCompagnieTransport(updatedCompagnieTransport)).thenReturn(compagnieTransportDto);

        CompagnieTransportDto result = underTest.updateCompagnieTransport(compagnieTransportDto, givenCompagnieTransportId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenCompagnieTransportNotFound() {
        Long givenCompagnieTransportId = 2L;
        CompagnieTransportDto compagnieTransportDto = CompagnieTransportDto.builder()
                .id(2L)
                .nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportRepository.findById(givenCompagnieTransportId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateCompagnieTransport(compagnieTransportDto, givenCompagnieTransportId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenCompagnieTransportId = 2L;
        CompagnieTransport compagnieTransport = CompagnieTransport.builder()
                .id(2)
                .nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto compagnieTransportDto = CompagnieTransportDto.builder()
                .id(2L)
                .nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportRepository.findById(givenCompagnieTransportId)).thenReturn(Optional.of(compagnieTransport));
        when(compagnieTransportRepository.saveAndFlush(compagnieTransport)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateCompagnieTransport(compagnieTransportDto, givenCompagnieTransportId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenCompagnieTransportDtoIsInvalid() {
        Long givenCompagnieTransportId = 2L;
        CompagnieTransport compagnieTransport = CompagnieTransport.builder()
                .id(2)
                .nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto invalidCompagnieTransportDto = CompagnieTransportDto.builder()
                .id(2L)
                .nom("Hanibaal").numTel(97852364).build();

        when(compagnieTransportRepository.findById(givenCompagnieTransportId)).thenReturn(Optional.of(compagnieTransport));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(compagnieTransportValidators).validate(any(CompagnieTransport.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateCompagnieTransport(invalidCompagnieTransportDto, givenCompagnieTransportId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    */
/****             Test Méthod Delete       **********//*


    @Test
    void shouldDeleteCompagnieTransportById() {
        Long CompagnieTransportId = 1L;
        CompagnieTransport compagnieTransport = CompagnieTransport.builder().id(1).nom("Hanibaal").numTel(97852364).build();
        CompagnieTransportDto compagnieTransportDto = CompagnieTransportDto.builder().id(1L).nom("Hanibaal").numTel(97852364).build();
        when(compagnieTransportRepository.findById(compagnieTransportDto.getId())).thenReturn(Optional.of(compagnieTransport));
        underTest.deleteCompagnieTransport(CompagnieTransportId);

        verify(compagnieTransportRepository,times(1)).findById(CompagnieTransportId);
        verify(compagnieTransportRepository,times(1)).deleteById(CompagnieTransportId);

    }

    @Test
    void shouldNotDeleteCompagnieTransportIfNotFound() {
        Long CompagnieTransportId = 1L;

        // Simuler que le CompagnieTransport n'existe pas en faisant en sorte que l'Optional soit vide
        when(compagnieTransportRepository.findById(CompagnieTransportId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteCompagnieTransport(CompagnieTransportId)).isInstanceOf(EntityNotFoundException.class).hasMessage("CompagnieTransport with this id Not Found");
    }

}


*/
