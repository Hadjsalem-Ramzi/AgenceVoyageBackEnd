package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.GuidePersonneMapper;
import com.hadjsalem.agencevoyage.repositories.GuidePersonneRepository;
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
class GuidePersonneServiceImplTest {
    
    @Mock
    private GuidePersonneRepository guidePersonneRepository;
    
    @Mock
    private GuidePersonneMapper guidePersonneMapper;
    
    @InjectMocks
    private GuidePersonneServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<GuidePersonne> guidePersonneValidators;

    @Test
    void shouldSaveNewGuidePersonne(){
        GuidePersonneDto guidePersonneDto = GuidePersonneDto.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonne guidePersonne= GuidePersonne.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonne savedGuidePersonne= GuidePersonne.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto expectedGuidePersonne= guidePersonneDto.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneMapper.fromGuidePersonneDto(guidePersonneDto)).thenReturn(guidePersonne);
        when(guidePersonneRepository.save(guidePersonne)).thenReturn(savedGuidePersonne);
        when(guidePersonneMapper.fromGuidePersonne(savedGuidePersonne)).thenReturn(expectedGuidePersonne);
        GuidePersonneDto result = underTest.saveGuidePersonne(guidePersonneDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedGuidePersonne);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenGuidePersonneExists() {
        GuidePersonneDto guidePersonneDto = GuidePersonneDto.builder()
                .firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        GuidePersonne guidePersonne = GuidePersonne.builder()
                .firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneMapper.fromGuidePersonneDto(guidePersonneDto)).thenReturn(guidePersonne);
        when(guidePersonneRepository.existsByNumTel(guidePersonneDto.getNumTel())).thenReturn(true);
        Mockito.doNothing().when(guidePersonneValidators).validate(Mockito.any(GuidePersonne.class));

        assertThatThrownBy(() -> underTest.saveGuidePersonne(guidePersonneDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("Un Guide Personne est existe avec cette Num télephone");

        verify(guidePersonneRepository, times(1)).existsByNumTel(guidePersonneDto.getNumTel());
        verify(guidePersonneRepository, never()).save(any(GuidePersonne.class));
    }
    @Test
    void ShouldGetAllGuidePersonnes() {
        GuidePersonne GuidePersonne1 = new GuidePersonne();
        GuidePersonne1.setId(1L);
        GuidePersonne GuidePersonne2 = new GuidePersonne();
        GuidePersonne2.setId(2L);
        List<GuidePersonne> GuidePersonnes = Arrays.asList(GuidePersonne1, GuidePersonne2);
        Page<GuidePersonne> pageGuidePersonnes = new PageImpl<>(GuidePersonnes);

        GuidePersonneDto guidePersonneDto1 = new GuidePersonneDto();
        guidePersonneDto1.setId(1L);
        GuidePersonneDto guidePersonneDto2 = new GuidePersonneDto();
        guidePersonneDto2.setId(2L);

        when(guidePersonneRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageGuidePersonnes);
        when(guidePersonneMapper.fromGuidePersonne(GuidePersonne1)).thenReturn(guidePersonneDto1);
        when(guidePersonneMapper.fromGuidePersonne(GuidePersonne2)).thenReturn(guidePersonneDto2);

        // When
        PageResponse<GuidePersonneDto> result = underTest.getGuidePersonnes(0, 2);

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
    void ShouldFindGuidePersonneById() {
        Long givenGuidePersonneId = 1L;
        GuidePersonne guidePersonne = GuidePersonne.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto expected = GuidePersonneDto.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        when(guidePersonneRepository.findById(givenGuidePersonneId)).thenReturn(Optional.of(guidePersonne));
        when(guidePersonneMapper.fromGuidePersonne(guidePersonne)).thenReturn(expected);
        GuidePersonneDto result = underTest.findGuidePersonneById(givenGuidePersonneId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindGuidePersonneById() {
        Long givenGuidePersonneById = 5l;
        when(guidePersonneRepository.findById(givenGuidePersonneById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findGuidePersonneById(givenGuidePersonneById)).isInstanceOf(EntityNotFoundException.class).hasMessage("GuidePersonne Not Found");

    }

    /*******                Test Method FindGuidePersonneByNumTel                    ********* */


    @Test
    void ShouldfindGuidePersonneByNumeroTélephone() {
        Integer givenNumTel = 54604022;
        GuidePersonne guidePersonne = GuidePersonne.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto expected = GuidePersonneDto.builder().firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneRepository.findPersonneByNumTelephone(givenNumTel)).thenReturn(Optional.of(guidePersonne));
        when(guidePersonneMapper.fromGuidePersonne(guidePersonne)).thenReturn(expected);

        GuidePersonneDto result = underTest.findGuidePersonneByNumTel(givenNumTel);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindGuidePersonneByNumeroTélephone() {
        Integer  givenNumTel= 54604022;

        when(guidePersonneRepository.findPersonneByNumTelephone(givenNumTel)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findGuidePersonneByNumTel(givenNumTel)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updateGuidePersonne ****/



    @Test
    void ShouldUpdateGuidePersonne() {
        Long givenGuidePersonneId = 2L;
        GuidePersonne guidePersonne = GuidePersonne.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto guidePersonneDto = GuidePersonneDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonne updatedGuidePersonne = GuidePersonne.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto expected = guidePersonneDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneRepository.findById(givenGuidePersonneId)).thenReturn(Optional.of(guidePersonne));
        when(guidePersonneRepository.saveAndFlush(guidePersonne)).thenReturn(updatedGuidePersonne);
        when(guidePersonneMapper.fromGuidePersonne(updatedGuidePersonne)).thenReturn(guidePersonneDto);

        GuidePersonneDto result = underTest.updateGuidePersonne(guidePersonneDto, givenGuidePersonneId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateGuidePersonne() {
        Long givenGuidePersonneId = 2L;
        GuidePersonne guidePersonne = GuidePersonne.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto guidePersonneDto = GuidePersonneDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonne updatedGuidePersonne = GuidePersonne.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto expected = guidePersonneDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneRepository.findById(givenGuidePersonneId)).thenReturn(Optional.of(guidePersonne));
        when(guidePersonneRepository.saveAndFlush(guidePersonne)).thenReturn(updatedGuidePersonne);
        when(guidePersonneMapper.fromGuidePersonne(updatedGuidePersonne)).thenReturn(guidePersonneDto);

        GuidePersonneDto result = underTest.updateGuidePersonne(guidePersonneDto, givenGuidePersonneId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenGuidePersonneNotFound() {
        Long givenGuidePersonneId = 2L;
        GuidePersonneDto guidePersonneDto = GuidePersonneDto.builder()
                .id(2L)
                .firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneRepository.findById(givenGuidePersonneId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateGuidePersonne(guidePersonneDto, givenGuidePersonneId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenGuidePersonneId = 2L;
        GuidePersonne guidePersonne = GuidePersonne.builder()
                .id(2L)
                .firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto guidePersonneDto = GuidePersonneDto.builder()
                .id(2L)
                .firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneRepository.findById(givenGuidePersonneId)).thenReturn(Optional.of(guidePersonne));
        when(guidePersonneRepository.saveAndFlush(guidePersonne)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateGuidePersonne(guidePersonneDto, givenGuidePersonneId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenGuidePersonneDtoIsInvalid() {
        Long givenGuidePersonneId = 2L;
        GuidePersonne guidePersonne = GuidePersonne.builder()
                .id(2L)
                .firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto invalidguidePersonneDto = GuidePersonneDto.builder()
                .id(2L)
                .firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();

        when(guidePersonneRepository.findById(givenGuidePersonneId)).thenReturn(Optional.of(guidePersonne));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(guidePersonneValidators).validate(any(GuidePersonne.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateGuidePersonne(invalidguidePersonneDto, givenGuidePersonneId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteGuidePersonneById() {
        Long GuidePersonneId = 1L;
        GuidePersonne guidePersonne = GuidePersonne.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        GuidePersonneDto guidePersonneDto = GuidePersonneDto.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTel(54604022).build();
        when(guidePersonneRepository.findById(guidePersonneDto.getId())).thenReturn(Optional.of(guidePersonne));
        underTest.deleteGuidePersonne(GuidePersonneId);

        verify(guidePersonneRepository,times(1)).findById(GuidePersonneId);
        verify(guidePersonneRepository,times(1)).deleteById(GuidePersonneId);

    }

    @Test
    void shouldNotDeleteGuidePersonneIfNotFound() {
        Long GuidePersonneId = 1L;

        // Simuler que le GuidePersonne n'existe pas en faisant en sorte que l'Optional soit vide
        when(guidePersonneRepository.findById(GuidePersonneId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteGuidePersonne(GuidePersonneId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Guide Personne with this id Not Found");
    }

}
    
    

