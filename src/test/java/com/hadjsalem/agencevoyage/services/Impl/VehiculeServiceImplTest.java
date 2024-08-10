package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import com.hadjsalem.agencevoyage.entities.Vehicule;
import com.hadjsalem.agencevoyage.entities.Vehicule;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.VehiculeMapper;
import com.hadjsalem.agencevoyage.repositories.VehiculeRepository;
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
class VehiculeServiceImplTest {

    @Mock
    private VehiculeMapper vehiculeMapper;

    @Mock
    private VehiculeRepository vehiculeRepository;

    @InjectMocks
    private VehiculeServiceImpl underTest;

    @Mock
    private ObjectsValidators<Vehicule> vehiculeValidators;
    @Test
    void shouldSaveNewVehicule(){
        VehiculeDto vehiculeDto = VehiculeDto.builder().immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        Vehicule vehicule= Vehicule.builder().immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        Vehicule savedVehicule= Vehicule.builder().id(1L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto expectedVehicule= VehiculeDto.builder().id(1L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeMapper.fromVehiculeDto(vehiculeDto)).thenReturn(vehicule);
        when(vehiculeRepository.save(vehicule)).thenReturn(savedVehicule);
        when(vehiculeMapper.fromVehicule(savedVehicule)).thenReturn(expectedVehicule);
        VehiculeDto result = underTest.saveVehicule(vehiculeDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedVehicule);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenVehiculeExists() {
        VehiculeDto vehiculeDto = VehiculeDto.builder()
                .immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        Vehicule vehicule = Vehicule.builder()
                .immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeMapper.fromVehiculeDto(vehiculeDto)).thenReturn(vehicule);
        when(vehiculeRepository.existsByImmatricule(vehiculeDto.getImmatricule())).thenReturn(true);
        Mockito.doNothing().when(vehiculeValidators).validate(Mockito.any(Vehicule.class));

        assertThatThrownBy(() -> underTest.saveVehicule(vehiculeDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("un Vehicule est existe avec ce nom");

        verify(vehiculeRepository, times(1)).existsByImmatricule(vehiculeDto.getImmatricule());
        verify(vehiculeRepository, never()).save(any(Vehicule.class));
    }
    @Test
    void ShouldGetAllVehicules() {
        Vehicule Vehicule1 = new Vehicule();
        Vehicule1.setId(1L);
        Vehicule Vehicule2 = new Vehicule();
        Vehicule2.setId(2L);
        List<Vehicule> Vehicules = Arrays.asList(Vehicule1, Vehicule2);
        Page<Vehicule> pageVehicules = new PageImpl<>(Vehicules);

        VehiculeDto VehiculeDto1 = new VehiculeDto();
        VehiculeDto1.setId(1L);
        VehiculeDto VehiculeDto2 = new VehiculeDto();
        VehiculeDto2.setId(2L);

        when(vehiculeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageVehicules);
        when(vehiculeMapper.fromVehicule(Vehicule1)).thenReturn(VehiculeDto1);
        when(vehiculeMapper.fromVehicule(Vehicule2)).thenReturn(VehiculeDto2);

        // When
        PageResponse<VehiculeDto> result = underTest.getVehicules(0, 2);

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
    void ShouldFindVehiculeById() {
        Long givenVehiculeId = 1L;
        Vehicule vehicule = Vehicule.builder().id(1L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto expected = VehiculeDto.builder().id(1L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        when(vehiculeRepository.findById(givenVehiculeId)).thenReturn(Optional.of(vehicule));
        when(vehiculeMapper.fromVehicule(vehicule)).thenReturn(expected);
        VehiculeDto result = underTest.findVehiculeById(givenVehiculeId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindVehiculeById() {
        Long givenVehiculeById = 5l;
        when(vehiculeRepository.findById(givenVehiculeById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findVehiculeById(givenVehiculeById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Vehicule Not Found");

    }

    /*******                Test Method FindVehiculeByNumTel                    ********* */


    @Test
    void ShouldfindVehiculeByImmatricule() {
        String givenImmatricule = "123 Tunis 700";
        Vehicule vehicule = Vehicule.builder().immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto expected = VehiculeDto.builder().immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeRepository.findVehiculeByImmatricule(givenImmatricule)).thenReturn(Optional.of(vehicule));
        when(vehiculeMapper.fromVehicule(vehicule)).thenReturn(expected);

        VehiculeDto result = underTest.findVehiculeByImmatricule(givenImmatricule);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindVehiculeByImmatricule() {
        String  givenImmatricule= "123 Tunis 700";

        when(vehiculeRepository.findVehiculeByImmatricule(givenImmatricule)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findVehiculeByImmatricule(givenImmatricule)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updateVehicule ****/



    @Test
    void ShouldUpdateVehicule() {
        Long givenVehiculeId = 2L;
        Vehicule vehicule = Vehicule.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto vehiculeDto = VehiculeDto.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        Vehicule updatedVehicule = Vehicule.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto expected = VehiculeDto.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeRepository.findById(givenVehiculeId)).thenReturn(Optional.of(vehicule));
        when(vehiculeRepository.saveAndFlush(vehicule)).thenReturn(updatedVehicule);
        when(vehiculeMapper.fromVehicule(updatedVehicule)).thenReturn(vehiculeDto);

        VehiculeDto result = underTest.updateVehicule(vehiculeDto, givenVehiculeId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdateVehicule() {
        Long givenVehiculeId = 2L;
        Vehicule vehicule = Vehicule.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto vehiculeDto = VehiculeDto.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        Vehicule updatedVehicule = Vehicule.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto expected = VehiculeDto.builder().id(2L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeRepository.findById(givenVehiculeId)).thenReturn(Optional.of(vehicule));
        when(vehiculeRepository.saveAndFlush(vehicule)).thenReturn(updatedVehicule);
        when(vehiculeMapper.fromVehicule(updatedVehicule)).thenReturn(vehiculeDto);

        VehiculeDto result = underTest.updateVehicule(vehiculeDto, givenVehiculeId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenVehiculeNotFound() {
        Long givenVehiculeId = 2L;
        VehiculeDto vehiculeDto = VehiculeDto.builder()
                .id(2L)
                .immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeRepository.findById(givenVehiculeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateVehicule(vehiculeDto, givenVehiculeId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenVehiculeId = 2L;
        Vehicule vehicule = Vehicule.builder()
                .id(2L)
                .immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto vehiculeDto = VehiculeDto.builder()
                .id(2L)
                .immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeRepository.findById(givenVehiculeId)).thenReturn(Optional.of(vehicule));
        when(vehiculeRepository.saveAndFlush(vehicule)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateVehicule(vehiculeDto, givenVehiculeId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenVehiculeDtoIsInvalid() {
        Long givenVehiculeId = 2L;
        Vehicule vehicule = Vehicule.builder()
                .id(2L)
                .immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto invalidVehiculeDto = VehiculeDto.builder()
                .id(2L)
                .immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();

        when(vehiculeRepository.findById(givenVehiculeId)).thenReturn(Optional.of(vehicule));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(vehiculeValidators).validate(any(Vehicule.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateVehicule(invalidVehiculeDto, givenVehiculeId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeleteVehiculeById() {
        Long VehiculeId = 1L;
        Vehicule vehicule = Vehicule.builder().id(1L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        VehiculeDto vehiculeDto = VehiculeDto.builder().id(1L).immatricule("123 Tunis 700").capacite(100).type("MiniBus").build();
        when(vehiculeRepository.findById(vehiculeDto.getId())).thenReturn(Optional.of(vehicule));
        underTest.deleteVehicule(VehiculeId);

        verify(vehiculeRepository,times(1)).findById(VehiculeId);
        verify(vehiculeRepository,times(1)).deleteById(VehiculeId);

    }

    @Test
    void shouldNotDeleteVehiculeIfNotFound() {
        Long VehiculeId = 1L;

        when(vehiculeRepository.findById(VehiculeId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteVehicule(VehiculeId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Vehicule with this id Not Found");
    }

}
