package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.HotelDto;

public interface HotelService {
    HotelDto findHotelById(Long id);
    HotelDto findHotelByLibelle(String libelle);
    HotelDto saveHotel( HotelDto hotel);
    HotelDto updateHotel( HotelDto hotel,Long id);
   void deleteHotel(Long id);

  PageResponse<HotelDto> getHotels(int page ,int size);


}
