package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuideDto;
import com.hadjsalem.agencevoyage.entities.Guide;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.GuideMapper;
import com.hadjsalem.agencevoyage.repositories.GuideRepository;
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
class GuideServiceImplTest {

    @Mock
    private GuideMapper guideMapper;
    
    @Mock
    private GuideRepository guideRepository;
    
   @InjectMocks
    private GuideServiceImpl underTest;
   
   @Mock
    private ObjectsValidators<Guide> guideValidators;

    @Test
    void shouldSaveNewguide(){
        GuideDto guideDto = GuideDto.builder().name("Ramzi").build();
        Guide guide= Guide.builder().name("Ramzi").build();
        Guide savedguide= guide.builder().id(1L).name("Ramzi").build();
        GuideDto expectedguide= guideDto.builder().id(1L).name("Ramzi").build();

        when(guideMapper.fromGuideDto(guideDto)).thenReturn(guide);
        when(guideRepository.save(guide)).thenReturn(savedguide);
        when(guideMapper.fromGuide(savedguide)).thenReturn(expectedguide);
        GuideDto result = underTest.saveGuide(guideDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedguide);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenguideExists() {
        GuideDto guideDto = GuideDto.builder().name("Ramzi").build();

        Guide guide = Guide.builder().name("Ramzi").build();

        when(guideMapper.fromGuideDto(guideDto)).thenReturn(guide);
        when(guideRepository.existsByName(guideDto.getName())).thenReturn(true);
        Mockito.doNothing().when(guideValidators).validate(Mockito.any(Guide.class));

        assertThatThrownBy(() -> underTest.saveGuide(guideDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage(" A Guid was found with this NumTélephone");

        verify(guideRepository, times(1)).existsByName(guideDto.getName());
        verify(guideRepository, never()).save(any(Guide.class));
    }
    @Test
    void ShouldGetAllguides() {
        Guide guide1 = new Guide();
        guide1.setId(1L);
        Guide guide2 = new Guide();
        guide2.setId(2L);
        List<Guide> guides = Arrays.asList(guide1, guide2);
        Page<Guide> pageguides = new PageImpl<>(guides);

        GuideDto guideDto1 = new GuideDto();
        guideDto1.setId(1L);
        GuideDto guideDto2 = new GuideDto();
        guideDto2.setId(2L);

        when(guideRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageguides);
        when(guideMapper.fromGuide(guide1)).thenReturn(guideDto1);
        when(guideMapper.fromGuide(guide2)).thenReturn(guideDto2);

        // When
        PageResponse<GuideDto> result = underTest.getGuides(0, 2);

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
    void ShouldFindguideById() {
        Long givenguideId = 1L;
        Guide guide = Guide.builder().id(1L).name("Ramzi").build();
        GuideDto expected = GuideDto.builder().id(1L).name("Ramzi").build();
        when(guideRepository.findById(givenguideId)).thenReturn(Optional.of(guide));
        when(guideMapper.fromGuide(guide)).thenReturn(expected);
        GuideDto result = underTest.findGuideById(givenguideId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindguideById() {
        Long givenguideById = 5l;
        when(guideRepository.findById(givenguideById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findGuideById(givenguideById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Guide Not Found");

    }

    /*******                Test Method FindguideByNumTel                    ********* */

    @Test
    void ShouldFindGuideByLibelle() {
        String libelle = "Ramzi";
        Guide guide = Guide.builder().name("Ramzi").build();
        GuideDto expected = GuideDto.builder().name("Ramzi").build();

        when(guideRepository.findGuideByName(libelle)).thenReturn(Optional.of(guide));
        when(guideMapper.fromGuide(guide)).thenReturn(expected);

        GuideDto result = underTest.findGuideByName(libelle);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }




    /***********    Test  Method updateguide ****/



    @Test
    void ShouldUpdateguide() {
        Long givenguideId = 2L;
        Guide guide = Guide.builder().id(2L).name("Ramzi").build();
        GuideDto guideDto = GuideDto.builder().id(2L).name("Ramzi").build();
        Guide updatedguide = guide.builder().id(2L).name("Ramzi").build();
        GuideDto expected = guideDto.builder().id(2L).name("Ramzi").build();

        when(guideRepository.findById(givenguideId)).thenReturn(Optional.of(guide));
        when(guideRepository.saveAndFlush(guide)).thenReturn(updatedguide);
        when(guideMapper.fromGuide(updatedguide)).thenReturn(guideDto);

        GuideDto result = underTest.updateGuide(guideDto, givenguideId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateguide() {
        Long givenguideId = 2L;
        Guide guide = Guide.builder().id(2L).name("Ramzi").build();
        GuideDto guideDto = GuideDto.builder().id(2L).name("Ramzi").build();
        Guide updatedguide = guide.builder().id(2L).name("Ramzi").build();
        GuideDto expected = guideDto.builder().id(2L).name("Ramzi").build();

        when(guideRepository.findById(givenguideId)).thenReturn(Optional.of(guide));
        when(guideRepository.saveAndFlush(guide)).thenReturn(updatedguide);
        when(guideMapper.fromGuide(updatedguide)).thenReturn(guideDto);

        GuideDto result = underTest.updateGuide(guideDto, givenguideId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenguideNotFound() {
        Long givenguideId = 2L;
        GuideDto guideDto = GuideDto.builder()
                .id(2L)
                .name("Ramzi").build();

        when(guideRepository.findById(givenguideId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateGuide(guideDto, givenguideId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenguideId = 2L;
        Guide guide = Guide.builder()
                .id(2L)
                .name("Ramzi").build();
        GuideDto guideDto = GuideDto.builder()
                .id(2L)
                .name("Ramzi").build();

        when(guideRepository.findById(givenguideId)).thenReturn(Optional.of(guide));
        when(guideRepository.saveAndFlush(guide)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateGuide(guideDto, givenguideId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenguideDtoIsInvalid() {
        Long givenguideId = 2L;
        Guide guide = Guide.builder()
                .id(2L)
                .name("Ramzi").build();
        GuideDto invalidguideDto = GuideDto.builder()
                .id(2L)
                .name("Ramzi").build();

        when(guideRepository.findById(givenguideId)).thenReturn(Optional.of(guide));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(guideValidators).validate(any(Guide.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateGuide(invalidguideDto, givenguideId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteguideById() {
        Long guideId = 1L;
        Guide guide = Guide.builder().id(1L).name("Ramzi").build();
        GuideDto guideDto = GuideDto.builder().id(1L).name("Ramzi").build();
        when(guideRepository.findById(guideDto.getId())).thenReturn(Optional.of(guide));
        underTest.deleteGuide(guideId);

        verify(guideRepository,times(1)).findById(guideId);
        verify(guideRepository,times(1)).deleteById(guideId);

    }

    @Test
    void shouldNotDeleteguideIfNotFound() {
        Long guideId = 1L;

        // Simuler que le guide n'existe pas en faisant en sorte que l'Optional soit vide
        when(guideRepository.findById(guideId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteGuide(guideId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Guide was Not Found");
    }

}
    
