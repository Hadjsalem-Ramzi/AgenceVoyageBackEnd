package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import com.hadjsalem.agencevoyage.entities.Destination;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.DestinationMapper;
import com.hadjsalem.agencevoyage.repositories.DestinationRepository;
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
class DestinationServiceImplTest {

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private DestinationMapper destinationMapper;
    
    @InjectMocks
    private DestinationServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<Destination> destinationValidators;



    @Test
    void shouldSaveNewDestination(){
        DestinationDto destinationDto = DestinationDto.builder().ville("Paris").pays("France").build();
        Destination destination= Destination.builder().ville("Paris").pays("France").build();
        Destination savedDestination= Destination.builder().id(1L).ville("Paris").pays("France").build();
        DestinationDto expectedDestination= DestinationDto.builder().id(1L).ville("Paris").pays("France").build();

        when(destinationMapper.fromdestinationDto(destinationDto)).thenReturn(destination);
        when(destinationRepository.save(destination)).thenReturn(savedDestination);
        when(destinationMapper.fromdestination(savedDestination)).thenReturn(expectedDestination);
        DestinationDto result = underTest.saveDestination(destinationDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedDestination);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenDestinationExists() {
        DestinationDto destinationDto = DestinationDto.builder()
                        .ville("Paris").pays("France").build();

        Destination destination = Destination.builder()
                .ville("Paris").pays("France").build();

        when(destinationMapper.fromdestinationDto(destinationDto)).thenReturn(destination);
        when(destinationRepository.existsByVille(destinationDto.getVille())).thenReturn(true);
        Mockito.doNothing().when(destinationValidators).validate(Mockito.any(Destination.class));

        assertThatThrownBy(() -> underTest.saveDestination(destinationDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un destination est existe avec ce nom");

        verify(destinationRepository, times(1)).existsByVille(destinationDto.getVille());
        verify(destinationRepository, never()).save(any(Destination.class));
    }
    @Test
    void ShouldGetAllDestinations() {
        Destination destination1 = new Destination();
        destination1.setId(1L);
        Destination destination2 = new Destination();
        destination2.setId(2L);
        List<Destination> destinations = Arrays.asList(destination1, destination2);
        Page<Destination> pageDestinations = new PageImpl<>(destinations);

        DestinationDto destinationDto1 = new DestinationDto();
        destinationDto1.setId(1L);
        DestinationDto destinationDto2 = new DestinationDto();
        destinationDto2.setId(2L);

        when(destinationRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageDestinations);
        when(destinationMapper.fromdestination(destination1)).thenReturn(destinationDto1);
        when(destinationMapper.fromdestination(destination2)).thenReturn(destinationDto2);

        // When
        PageResponse<DestinationDto> result = underTest.getDestination(0, 2);

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
    void ShouldFindDestinationById() {
        Long givenDestinationId = 1L;
        Destination destination = Destination.builder().id(1L).ville("Paris").pays("France").build();
        DestinationDto expected = DestinationDto.builder().id(1L).ville("Paris").pays("France").build();
        when(destinationRepository.findById(givenDestinationId)).thenReturn(Optional.of(destination));
        when(destinationMapper.fromdestination(destination)).thenReturn(expected);
        DestinationDto result = underTest.findDestinationById(givenDestinationId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindDestinationById() {
        Long givenDestinationById = 5l;
        when(destinationRepository.findById(givenDestinationById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findDestinationById(givenDestinationById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Destination Not Found");

    }

/*                Test Method FindDestinationByNumTel                    **********/


    @Test
    void ShouldfindDestinationByNom() {
        String givenVille = "Paris";
        Destination destination = Destination.builder().ville("Paris").pays("France").build();
        DestinationDto expected = DestinationDto.builder().ville("Paris").pays("France").build();

        when(destinationRepository.findDestinationByVille(givenVille)).thenReturn(Optional.of(destination));
        when(destinationMapper.fromdestination(destination)).thenReturn(expected);

        DestinationDto result = underTest.findDestinationByVille(givenVille);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindDestinationByVille() {
        String  givenVille= "Paris";

        when(destinationRepository.findDestinationByVille(givenVille)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findDestinationByVille(givenVille)).isInstanceOf(EntityNotFoundException.class);
    }


/*    Test  Method updateDestination ****/



    @Test
    void ShouldUpdateDestination() {
        Long givenDestinationId = 2L;
        Destination destination = Destination.builder().id(2L).ville("Paris").pays("France").build();
        DestinationDto destinationDto = DestinationDto.builder().id(2L).ville("Paris").pays("France").build();
        Destination updatedDestination = Destination.builder().id(2L).ville("Paris").pays("France").build();
        DestinationDto expected = DestinationDto.builder().id(2L).ville("Paris").pays("France").build();

        when(destinationRepository.findById(givenDestinationId)).thenReturn(Optional.of(destination));
        when(destinationRepository.saveAndFlush(destination)).thenReturn(updatedDestination);
        when(destinationMapper.fromdestination(updatedDestination)).thenReturn(destinationDto);

        DestinationDto result = underTest.updateDestination(destinationDto, givenDestinationId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateDestination() {
        Long givenDestinationId = 2L;
        Destination destination = Destination.builder().id(2L).ville("Paris").pays("France").build();
        DestinationDto destinationDto = DestinationDto.builder().id(2L).ville("Paris").pays("France").build();
        Destination updatedDestination = Destination.builder().id(2L).ville("Paris").pays("France").build();
        DestinationDto expected = DestinationDto.builder().id(2L).ville("Paris").pays("France").build();

        when(destinationRepository.findById(givenDestinationId)).thenReturn(Optional.of(destination));
        when(destinationRepository.saveAndFlush(destination)).thenReturn(updatedDestination);
        when(destinationMapper.fromdestination(updatedDestination)).thenReturn(destinationDto);

        DestinationDto result = underTest.updateDestination(destinationDto, givenDestinationId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenDestinationNotFound() {
        Long givenDestinationId = 2L;
        DestinationDto destinationDto = DestinationDto.builder()
                .id(2L)
                .ville("Paris").pays("France").build();

        when(destinationRepository.findById(givenDestinationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateDestination(destinationDto, givenDestinationId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenDestinationId = 2L;
        Destination destination = Destination.builder()
                .id(2L)
                .ville("Paris").pays("France").build();
        DestinationDto destinationDto = DestinationDto.builder()
                .id(2L)
                .ville("Paris").pays("France").build();

        when(destinationRepository.findById(givenDestinationId)).thenReturn(Optional.of(destination));
        when(destinationRepository.saveAndFlush(destination)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateDestination(destinationDto, givenDestinationId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenDestinationDtoIsInvalid() {
        Long givenDestinationId = 2L;
        Destination destination = Destination.builder()
                .id(2L)
                .ville("Paris").pays("France").build();
        DestinationDto invalidDestinationDto = DestinationDto.builder()
                .id(2L)
                .ville("Paris").pays("France").build();

        when(destinationRepository.findById(givenDestinationId)).thenReturn(Optional.of(destination));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("cette champ  ne peut pas être vide")))
                .when(destinationValidators).validate(any(Destination.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateDestination(invalidDestinationDto, givenDestinationId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("cette champ  ne peut pas être vide");
    }



/**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteDestinationById() {
        Long DestinationId = 1L;
        Destination destination = Destination.builder().id(1L).ville("Paris").pays("France").build();
        DestinationDto destinationDto = DestinationDto.builder().id(1L).ville("Paris").pays("France").build();
        when(destinationRepository.findById(destinationDto.getId())).thenReturn(Optional.of(destination));
        underTest.deleteDestination(DestinationId);

        verify(destinationRepository,times(1)).findById(DestinationId);
        verify(destinationRepository,times(1)).deleteById(DestinationId);

    }

    @Test
    void shouldNotDeleteDestinationIfNotFound() {
        Long DestinationId = 1L;

        // Simuler que le Destination n'existe pas en faisant en sorte que l'Optional soit vide
        when(destinationRepository.findById(DestinationId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteDestination(DestinationId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Destination with this id Not Found");
    }

}
    
    
    

