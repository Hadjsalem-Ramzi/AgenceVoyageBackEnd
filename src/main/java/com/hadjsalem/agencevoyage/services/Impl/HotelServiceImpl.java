package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.HotelDto;
import com.hadjsalem.agencevoyage.entities.Guide;
import com.hadjsalem.agencevoyage.entities.Hotel;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.HotelMapper;
import com.hadjsalem.agencevoyage.repositories.HotelRepository;
import com.hadjsalem.agencevoyage.services.HotelService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class HotelServiceImpl implements HotelService {
    private HotelRepository hotelRepository;
    private HotelMapper mapper;
    private ObjectsValidators<Hotel> hotelValidators;


    @Override
    public HotelDto findHotelById(Long id) {
    Optional<Hotel> optionalHotel = hotelRepository.findById(id);
    return optionalHotel.map(mapper::fromHotel).orElseThrow(()->new NoSuchElementException("Hotel Not Found"));

    }

    @Override
    public HotelDto findHotelByLibelle(String email) {
        Optional<Hotel> hotel= hotelRepository.findHotelByLibelle(email);
        if (!hotel.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromHotel(hotel.get());
    }
    @Override
    public HotelDto saveHotel(HotelDto hotelDto) {
     Hotel hotel = mapper.fromHotelDto(hotelDto);
     if( hotel == null){
         throw new IllegalArgumentException("Hotel est null");
     }
     hotelValidators.validate(hotel);
     boolean exists = hotelRepository.existsByLibelle(hotelDto.getLibelle());
     if (exists){
         throw new DuplicateEntryException("A Hotel was found with this Libelle");
     }
     Hotel savedHotel = hotelRepository.save(hotel);
     return mapper.fromHotel(hotel);
    }

    @Override
    public HotelDto updateHotel(HotelDto hoteldto, Long id) {
      Optional<Hotel>hotel1 =hotelRepository.findById(id);
      if(hotel1.isPresent()){
     Hotel hotel2 =hotel1.get();
      hotel2.setId(id);
      hotelValidators.validate(hotel2);
      if(hotelRepository.existsByLibelle(hotel2.getLibelle())){
          throw  new DuplicateEntryException("A Hotel was Found with this Libelle");
      }
      Hotel hotel3 = hotelRepository.saveAndFlush(hotel2);
      return  mapper.fromHotel(hotel3);
      }else {
          throw  new EntityNotFoundException("Hotel NotFound");
      }
    }

    @Override
    public void deleteHotel(Long id) {
        if(hotelRepository.findById(id).isPresent()){
            hotelRepository.deleteById(id);
        } else {
            throw  new EntityNotFoundException("Hotel with this id Not Found");
        }

    }

    public PageResponse<HotelDto> getHotels(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Hotel> Hotels = hotelRepository.findAll(pageRequest);
        List<HotelDto> HotelList = Hotels.map(mapper::fromHotel).getContent();

        return new PageResponse<>(
                HotelList,
                Hotels.getNumber(),
                Hotels.getSize(),
                Hotels.getTotalElements(),
                Hotels.getTotalPages(),
                Hotels.isFirst(),
                Hotels.isLast()
        );
    }
}
