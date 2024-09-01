package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.HotelApi;
import com.hadjsalem.agencevoyage.dtos.HotelDto;
import com.hadjsalem.agencevoyage.services.Impl.HotelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelController implements HotelApi {

    private  final HotelServiceImpl hotelService ;


    @Override
    public HotelDto saveHotel(HotelDto Hotel) {
        return hotelService.saveHotel(Hotel) ;
    }

    @Override
    public HotelDto findHotelById(Long id) {
        return hotelService.findHotelById(id) ;
    }

    @Override
    public HotelDto findHotelByLibelle(String libelle) {
        return hotelService.findHotelByLibelle(libelle);
    }

    @Override
    public HotelDto updateHotel(HotelDto Hotel, Long id) {
        return hotelService.updateHotel(Hotel, id) ;
    }

    @Override
    public void deleteHotel(Long id) {
      hotelService.deleteHotel(id);
    }

    @Override
    public PageResponse<HotelDto> getHotels(int page, int size) {
        return hotelService.getHotels(page, size) ;
    }
}
