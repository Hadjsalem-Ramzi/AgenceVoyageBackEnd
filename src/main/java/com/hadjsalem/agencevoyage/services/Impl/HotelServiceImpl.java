package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.dtos.HotelDto;
import com.hadjsalem.agencevoyage.entities.Guide;
import com.hadjsalem.agencevoyage.entities.Hotel;
import com.hadjsalem.agencevoyage.mapper.HotelMapper;
import com.hadjsalem.agencevoyage.repositories.HotelRepository;
import com.hadjsalem.agencevoyage.services.HotelService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class HotelServiceImpl implements HotelService {
    private HotelRepository hotelRepository;
    private HotelMapper mapper;


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
    public HotelDto saveHotel(HotelDto HotelDto) {
     Hotel Hotel1 = mapper.fromHotelDto(HotelDto);
     Hotel Hotel2=hotelRepository.save(Hotel1);
      return mapper.fromHotel(Hotel2);
    }

    @Override
    public HotelDto updateHotel(HotelDto Hoteldto, Long id) {
      Optional<Hotel>Hotel1 =hotelRepository.findById(id);
      if(Hotel1.isPresent()){
     Hotel Hotel2 =Hotel1.get();
     Hotel Hotel3=hotelRepository.saveAndFlush(Hotel2);
      return mapper.fromHotel(Hotel3);
      }else {
          throw  new NoSuchElementException("Hotel NotFound");
      }
    }

    @Override
    public void deleteHotel(Long id) {
      hotelRepository.deleteById(id);
    }
}
