package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.HotelDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface HotelApi {

    @PostMapping(value = APP_ROOT+"/Hotels/saveHotel",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    HotelDto saveHotel(@RequestBody  HotelDto Hotel);

    @GetMapping(value = APP_ROOT +"/Hotels/{idHotel}",produces = MediaType.APPLICATION_JSON_VALUE)
    HotelDto findHotelById( @PathVariable("idHotel") Long id);

    @GetMapping(value = APP_ROOT +"/Hotels/{libelle}",produces = MediaType.APPLICATION_JSON_VALUE)
    HotelDto findHotelByLibelle(@PathVariable("libelle") String libelle);

    @GetMapping(value = APP_ROOT +"/Hotels/update/{idHotel}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    HotelDto updateHotel(@RequestBody HotelDto Hotel,@PathVariable("idHotel") Long id);

    @GetMapping(value = APP_ROOT +"/Hotels/delete/{idHotel}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteHotel(@PathVariable("idHotel") Long id);

    @GetMapping(value = APP_ROOT +"/Hotels/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<HotelDto> getHotels(@RequestParam("page") int page,@RequestParam("size") int size);


}
