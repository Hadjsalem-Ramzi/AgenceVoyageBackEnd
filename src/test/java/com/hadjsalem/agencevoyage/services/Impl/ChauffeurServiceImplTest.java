package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.ChauffeurMapper;
import com.hadjsalem.agencevoyage.repositories.ChauffeurRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChauffeurServiceImplTest {
    @Mock
    private ChauffeurRepository chauffeurRepository;

    @Mock
    private ChauffeurMapper chauffeurMapper;
    @InjectMocks
    private ChauffeurServiceImpl underTest;
    @Mock
    private ObjectsValidators<Chauffeur> chauffeurValidators;


    /****   Test for save Method  */
    @Test
    void shouldSaveNewChauffeur() {
        ChauffeurDto chauffeurDto = ChauffeurDto.builder()
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .numTelephone(85692345)
                .build();

        Chauffeur chauffeur = Chauffeur.builder()
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .numTelephone(85692345)
                .build();

        Chauffeur savedChauffeur = Chauffeur.builder()
                .id(1L)
                .firstName("Ali")
                .lastName("Hadjsalem")
                .numTelephone(85692345)
                .build();

        ChauffeurDto expectedChauffeur = ChauffeurDto.builder()
                .id(1L)
                .firstName("Ali")
                .lastName("Hadjsalem")
                .numTelephone(85692345)
                .build();

        when(chauffeurMapper.fromChauffeurDto(chauffeurDto)).thenReturn(chauffeur);
        when(chauffeurRepository.existsByNumTelephone(chauffeurDto.getNumTelephone())).thenReturn(false);
        when(chauffeurRepository.save(chauffeur)).thenReturn(savedChauffeur);
        when(chauffeurMapper.fromChaufeur(savedChauffeur)).thenReturn(expectedChauffeur);
        Mockito.doNothing().when(chauffeurValidators).validate(Mockito.any(Chauffeur.class));
        ChauffeurDto result = underTest.saveChauffeur(chauffeurDto);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedChauffeur);
        // Vérifiez que la méthode validate est appelée une fois
        Mockito.verify(chauffeurValidators, Mockito.times(1)).validate(chauffeur);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenChauffeurExists() {
        ChauffeurDto chauffeurDto = ChauffeurDto.builder()
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .numTelephone(85692345)
                .build();

        // Simule que le chauffeur existe déjà
        when(chauffeurRepository.existsByNumTelephone(chauffeurDto.getNumTelephone())).thenReturn(true);
        // Simule que la validation ne renvoie pas d'exception
        Mockito.doNothing().when(chauffeurValidators).validate(Mockito.any(Chauffeur.class));
        // Simule la conversion du DTO en entité
        when(chauffeurMapper.fromChauffeurDto(chauffeurDto)).thenReturn(new Chauffeur());

        // Vérifie que l'exception DuplicateEntryException est lancée
        assertThrows(DuplicateEntryException.class, () -> underTest.saveChauffeur(chauffeurDto));

        // Vérifie que existsByNumTelephone est appelé une fois
        Mockito.verify(chauffeurRepository, Mockito.times(1)).existsByNumTelephone(chauffeurDto.getNumTelephone());
        // Vérifie que validate est appelé une fois
        Mockito.verify(chauffeurValidators, Mockito.times(1)).validate(Mockito.any(Chauffeur.class));
    }

    /*******************************************************************************************************************************/

    /***  Test For GetAll Method   */


    @Test
    void ShouldGetAllChauffeurs() {
        Chauffeur chauffeur1 = new Chauffeur();
        chauffeur1.setId(1L);
        Chauffeur chauffeur2 = new Chauffeur();
        chauffeur2.setId(2L);
        List<Chauffeur> chauffeurs = Arrays.asList(chauffeur1, chauffeur2);
        Page<Chauffeur> pageChauffeurs = new PageImpl<>(chauffeurs);

        ChauffeurDto chauffeurDto1 = new ChauffeurDto();
        chauffeurDto1.setId(1L);
        ChauffeurDto chauffeurDto2 = new ChauffeurDto();
        chauffeurDto2.setId(2L);

        when(chauffeurRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageChauffeurs);
        when(chauffeurMapper.fromChaufeur(chauffeur1)).thenReturn(chauffeurDto1);
        when(chauffeurMapper.fromChaufeur(chauffeur2)).thenReturn(chauffeurDto2);

        // When
        PageResponse<ChauffeurDto> result = underTest.getChauffeurs(0, 2);

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
/******************************/
    /**
     * Test GetById Method
     */
    @Test
    void ShouldFindChauffeurById() {
        Long givenChauffeurId = 1L;
        Chauffeur chauffeur = Chauffeur.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(99548511).build();
        ChauffeurDto expected = ChauffeurDto.builder().id(1L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(99548511).build();
        when(chauffeurRepository.findById(givenChauffeurId)).thenReturn(Optional.of(chauffeur));
        when(chauffeurMapper.fromChaufeur(chauffeur)).thenReturn(expected);
        ChauffeurDto result = underTest.findChauffeurById(givenChauffeurId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindChauffeurById() {
        Long givenChauffeurById = 5l;
        when(chauffeurRepository.findById(givenChauffeurById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findChauffeurById(givenChauffeurById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Chauffeur Not Found");

    }

    /***                Test Method FindChauffeurByNumTel                    ************/
    @Test
    void ShouldfindChauffeurByNumTelephone() {
        Integer givenNumTel = 54604022;
        Chauffeur chauffeur = Chauffeur.builder().firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();
        ChauffeurDto expected = ChauffeurDto.builder().firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();

        when(chauffeurRepository.findChauffeurByNumTelephone(givenNumTel)).thenReturn(Optional.of(chauffeur));
        when(chauffeurMapper.fromChaufeur(chauffeur)).thenReturn(expected);

        ChauffeurDto result = underTest.findChauffeurByNumTelephone(givenNumTel);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindChauffeurByNumTelephone() {
        Integer givenNumTel = 546040698;

        when(chauffeurRepository.findChauffeurByNumTelephone(givenNumTel)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findChauffeurByNumTelephone(givenNumTel)).isInstanceOf(EntityNotFoundException.class);
    }

/**********************************************************************************************/
    /***    Test  Method updateChauffeur ******/

    @Test
    void ShouldUpdateChauffeur() {
        Long givenChaufferId = 2L;
        Chauffeur chauffeur = Chauffeur.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();
        ChauffeurDto chauffeurDto = ChauffeurDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();
        Chauffeur updatedchauffeur = Chauffeur.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();
        ChauffeurDto expected = ChauffeurDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();

        when(chauffeurRepository.findById(givenChaufferId)).thenReturn(Optional.of(chauffeur));
        when(chauffeurMapper.fromChaufeur(chauffeur)).thenReturn(chauffeurDto);
        when(chauffeurRepository.saveAndFlush(chauffeur)).thenReturn(updatedchauffeur);
        when(chauffeurMapper.fromChaufeur(updatedchauffeur)).thenReturn(chauffeurDto);

        ChauffeurDto result = underTest.updateChauffeur(chauffeurDto, givenChaufferId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateChauffeur() {
        Long givenChaufferId = 2L;
        Chauffeur chauffeur = Chauffeur.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();
        ChauffeurDto chauffeurDto = ChauffeurDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();
        Chauffeur updatedchauffeur = Chauffeur.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();
        ChauffeurDto expected = ChauffeurDto.builder().id(2L).firstName("Ramzi").lastName("Hadjsalem").numTelephone(54604022).build();

        when(chauffeurRepository.findById(givenChaufferId)).thenReturn(Optional.of(chauffeur));
        when(chauffeurMapper.fromChaufeur(chauffeur)).thenReturn(chauffeurDto);
        when(chauffeurRepository.saveAndFlush(chauffeur)).thenReturn(updatedchauffeur);
        when(chauffeurMapper.fromChaufeur(updatedchauffeur)).thenReturn(chauffeurDto);

        ChauffeurDto result = underTest.updateChauffeur(chauffeurDto, givenChaufferId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenChauffeurNotFound() {
        Long givenChauffeurId = 2L;
        ChauffeurDto chauffeurDto = ChauffeurDto.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .numTelephone(54604022)
                .build();

        when(chauffeurRepository.findById(givenChauffeurId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateChauffeur(chauffeurDto, givenChauffeurId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenChauffeurId = 2L;
        Chauffeur chauffeur = Chauffeur.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .numTelephone(54604022)
                .build();
        ChauffeurDto chauffeurDto = ChauffeurDto.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .numTelephone(54604022)
                .build();

        when(chauffeurRepository.findById(givenChauffeurId)).thenReturn(Optional.of(chauffeur));
        when(chauffeurRepository.saveAndFlush(chauffeur)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateChauffeur(chauffeurDto, givenChauffeurId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenChauffeurDtoIsInvalid() {
        Long givenChauffeurId = 2L;
        Chauffeur chauffeur = Chauffeur.builder()
                .id(2L)
                .firstName("Ramzi")
                .lastName("Hadjsalem")
                .numTelephone(54604022)
                .build();
        ChauffeurDto invalidChauffeurDto = ChauffeurDto.builder()
                .id(2L)
                .firstName("") // Invalid data
                .lastName("Hadjsalem")
                .numTelephone(54604022)
                .build();

        when(chauffeurRepository.findById(givenChauffeurId)).thenReturn(Optional.of(chauffeur));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(chauffeurValidators).validate(any(Chauffeur.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateChauffeur(invalidChauffeurDto, givenChauffeurId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /****             Test Méthod Delete       **********/

    @Test
    void shouldDeleteChauffeurById() {
        Long chauffeurId = 1L;

        underTest.deleteChauffeur(chauffeurId);

        verify(chauffeurRepository, times(1)).deleteById(chauffeurId);
    }

    @Test
    void shouldNotDeleteChauffeurIfNotFound() {
        Long chauffeurId = 1L;

        // Simuler que le chauffeur n'existe pas en faisant en sorte que l'Optional soit vide
        when(chauffeurRepository.findById(chauffeurId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteChauffeur(chauffeurId)).isInstanceOf(EntityNotFoundException.class).hasMessage("chauffeur with this id not Found");
    }

}










