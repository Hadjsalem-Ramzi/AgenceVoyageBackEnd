package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.HotelDto;
import com.hadjsalem.agencevoyage.entities.Hotel;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import com.hadjsalem.agencevoyage.mapper.HotelMapper;
import com.hadjsalem.agencevoyage.repositories.HotelRepository;
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
class HotelServiceImplTest {
    
    @Mock
    private HotelMapper hotelMapper;
    
    @Mock
    private HotelRepository hotelRepository;
    
    @InjectMocks
    private HotelServiceImpl underTest;
    
    @Mock
    private ObjectsValidators<Hotel> hotelValidators;
    @Test
    void shouldSaveNewhotel(){
        HotelDto hotelDto = HotelDto.builder().libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        Hotel hotel= Hotel.builder().libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        Hotel savedhotel= hotel.builder().id(1L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto expectedhotel= hotelDto.builder().id(1L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelMapper.fromHotelDto(hotelDto)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(savedhotel);
        when(hotelMapper.fromHotel(savedhotel)).thenReturn(expectedhotel);
        HotelDto result = underTest.saveHotel(hotelDto);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedhotel);
    }

    @Test
    void shouldThrowDuplicateEntryExceptionWhenhotelExists() {
        HotelDto hotelDto = HotelDto.builder()
                .libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        Hotel hotel = Hotel.builder()
                .libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelMapper.fromHotelDto(hotelDto)).thenReturn(hotel);
        when(hotelRepository.existsByLibelle(hotelDto.getLibelle())).thenReturn(true);
        Mockito.doNothing().when(hotelValidators).validate(Mockito.any(Hotel.class));

        assertThatThrownBy(() -> underTest.saveHotel(hotelDto))
                .isInstanceOf(DuplicateEntryException.class)
                .hasMessage("A Hotel was found with this Libelle");

        verify(hotelRepository, times(1)).existsByLibelle(hotelDto.getLibelle());
        verify(hotelRepository, never()).save(any(Hotel.class));
    }
    @Test
    void ShouldGetAllhotels() {
        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        Hotel hotel2 = new Hotel();
        hotel2.setId(2L);
        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);
        Page<Hotel> pagehotels = new PageImpl<>(hotels);

        HotelDto hotelDto1 = new HotelDto();
        hotelDto1.setId(1L);
        HotelDto hotelDto2 = new HotelDto();
        hotelDto2.setId(2L);

        when(hotelRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pagehotels);
        when(hotelMapper.fromHotel(hotel1)).thenReturn(hotelDto1);
        when(hotelMapper.fromHotel(hotel2)).thenReturn(hotelDto2);

        // When
        PageResponse<HotelDto> result = underTest.getHotels(0, 2);

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
    void ShouldFindHotelById() {
        Long givenhotelId = 1L;
        Hotel hotel = Hotel.builder().id(1L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto expected = HotelDto.builder().id(1L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        when(hotelRepository.findById(givenhotelId)).thenReturn(Optional.of(hotel));
        when(hotelMapper.fromHotel(hotel)).thenReturn(expected);
        HotelDto result = underTest.findHotelById(givenhotelId);
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNotFindHotelById() {
        Long givenhotelById = 5l;
        when(hotelRepository.findById(givenhotelById)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findHotelById(givenhotelById)).isInstanceOf(EntityNotFoundException.class).hasMessage("Hotel Not Found");

    }

    /*******                Test Method FindhotelByNumTel                    ********* */


    @Test
    void ShouldfindHotelByLibelle() {
        String givenLibelle = "Hannibaal";
        Hotel hotel = Hotel.builder().libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto expected = HotelDto.builder().libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelRepository.findHotelByLibelle(givenLibelle)).thenReturn(Optional.of(hotel));
        when(hotelMapper.fromHotel(hotel)).thenReturn(expected);

        HotelDto result = underTest.findHotelByLibelle(givenLibelle);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void ShouldNotfindHotelByVille() {
       String  givenLibelle= "hannibaal";

        when(hotelRepository.findHotelByLibelle(givenLibelle)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findHotelByLibelle(givenLibelle)).isInstanceOf(EntityNotFoundException.class);
    }


    /***********    Test  Method updatehotel ****/



    @Test
    void ShouldUpdatehotel() {
        Long givenhotelId = 2L;
        Hotel hotel = Hotel.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto hotelDto = HotelDto.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        Hotel updatedhotel = hotel.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto expected = hotelDto.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelRepository.findById(givenhotelId)).thenReturn(Optional.of(hotel));
        when(hotelRepository.saveAndFlush(hotel)).thenReturn(updatedhotel);
        when(hotelMapper.fromHotel(updatedhotel)).thenReturn(hotelDto);

        HotelDto result = underTest.updateHotel(hotelDto, givenhotelId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void ShouldNotUpdatehotel() {
        Long givenhotelId = 2L;
        Hotel hotel = Hotel.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto hotelDto = HotelDto.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        Hotel updatedhotel = hotel.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto expected = hotelDto.builder().id(2L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelRepository.findById(givenhotelId)).thenReturn(Optional.of(hotel));
        when(hotelRepository.saveAndFlush(hotel)).thenReturn(updatedhotel);
        when(hotelMapper.fromHotel(updatedhotel)).thenReturn(hotelDto);

        HotelDto result = underTest.updateHotel(hotelDto, givenhotelId);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    void shouldReturnExceptionWhenhotelNotFound() {
        Long givenhotelId = 2L;
        HotelDto hotelDto = HotelDto.builder()
                .id(2L)
                .libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelRepository.findById(givenhotelId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateHotel(hotelDto, givenhotelId)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenUpdateFails() {
        Long givenhotelId = 2L;
        Hotel hotel = Hotel.builder()
                .id(2L)
                .libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto hotelDto = HotelDto.builder()
                .id(2L)
                .libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelRepository.findById(givenhotelId)).thenReturn(Optional.of(hotel));
        when(hotelRepository.saveAndFlush(hotel)).thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> underTest.updateHotel(hotelDto, givenhotelId)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void shouldReturnExceptionWhenhotelDtoIsInvalid() {
        Long givenhotelId = 2L;
        Hotel hotel = Hotel.builder()
                .id(2L)
                .libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto invalidhotelDto = HotelDto.builder()
                .id(2L)
                .libelle("Hannibaal").caracteristique("*****").numberBed(100).build();

        when(hotelRepository.findById(givenhotelId)).thenReturn(Optional.of(hotel));

        // Simuler l'exception de validation
        doThrow(new ObjectNotValidException(Set.of("Le prénom ne peut pas être vide")))
                .when(hotelValidators).validate(any(Hotel.class));

        // Assertion pour vérifier l'exception
        assertThatThrownBy(() -> underTest.updateHotel(invalidhotelDto, givenhotelId))
                .isInstanceOf(ObjectNotValidException.class)
                .hasMessageContaining("Le prénom ne peut pas être vide");
    }



    /**             Test Méthod Delete       ********/



    @Test
    void shouldDeletehotelById() {
        Long hotelId = 1L;
        Hotel hotel = Hotel.builder().id(1L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        HotelDto hotelDto = HotelDto.builder().id(1L).libelle("Hannibaal").caracteristique("*****").numberBed(100).build();
        when(hotelRepository.findById(hotelDto.getId())).thenReturn(Optional.of(hotel));
        underTest.deleteHotel(hotelId);

        verify(hotelRepository,times(1)).findById(hotelId);
        verify(hotelRepository,times(1)).deleteById(hotelId);

    }

    @Test
    void shouldNotDeletehotelIfNotFound() {
        Long hotelId = 1L;

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->underTest.deleteHotel(hotelId)).isInstanceOf(EntityNotFoundException.class).hasMessage("Hotel with this id Not Found");
    }

}
