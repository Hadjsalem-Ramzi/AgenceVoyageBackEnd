package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface SocieteLocationApi {

    @PostMapping(value = APP_ROOT+"/SocieteLocations/saveSocieteLocation",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    SocieteLocationDto saveSocieteLocation(@RequestBody  SocieteLocationDto SocieteLocation);

    @GetMapping(value = APP_ROOT +"/SocieteLocations/{idSocieteLocation}",produces = MediaType.APPLICATION_JSON_VALUE)
    SocieteLocationDto findSocieteLocationById( @PathVariable("idSocieteLocation") Long id);

    @GetMapping(value = APP_ROOT +"/SocieteLocations/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    SocieteLocationDto findSocieteLocationByName(@PathVariable("name") String name);

    @GetMapping(value = APP_ROOT +"/SocieteLocations/update/{idSocieteLocation}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    SocieteLocationDto updateSocieteLocation(@RequestBody SocieteLocationDto SocieteLocation,@PathVariable("idSocieteLocation") Long id);

    @GetMapping(value = APP_ROOT +"/SocieteLocations/delete/{idSocieteLocation}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteSocieteLocation(@PathVariable("idSocieteLocation") Long id);

    @GetMapping(value = APP_ROOT +"/SocieteLocations/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<SocieteLocationDto> getSocieteLocations(@RequestParam("page") int page,@RequestParam("size") int size);


}
