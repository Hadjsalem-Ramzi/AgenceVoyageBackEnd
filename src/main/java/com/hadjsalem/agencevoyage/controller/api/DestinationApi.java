package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface DestinationApi {

    @PostMapping(value = APP_ROOT+"/Destinations/saveDestination",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    DestinationDto saveDestination(@RequestBody  DestinationDto Destination);

    @GetMapping(value = APP_ROOT +"/Destinations/{idDestination}",produces = MediaType.APPLICATION_JSON_VALUE)
    DestinationDto findDestinationById( @PathVariable("idDestination") Long id);

    @GetMapping(value = APP_ROOT +"/Destinations/{ville}",produces = MediaType.APPLICATION_JSON_VALUE)
    DestinationDto findDestinationByVille(@PathVariable("ville") String ville);

    @GetMapping(value = APP_ROOT +"/Destinations/update/{idDestination}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    DestinationDto updateDestination(@RequestBody DestinationDto Destination,@PathVariable("idDestination") Long id);

    @GetMapping(value = APP_ROOT +"/Destinations/delete/{idDestination}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteDestination(@PathVariable("idDestination") Long id);

    @GetMapping(value = APP_ROOT +"/Destinations/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<DestinationDto> getDestinations(@RequestParam("page") int page,@RequestParam("size") int size);


}
