package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface VehiculeApi {

    @PostMapping(value = APP_ROOT+"/Vehicules/saveVehicule",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeDto saveVehicule(@RequestBody  VehiculeDto Vehicule);

    @GetMapping(value = APP_ROOT +"/Vehicules/{idVehicule}",produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeDto findVehiculeById( @PathVariable("idVehicule") Long id);

    @GetMapping(value = APP_ROOT +"/Vehicules/{immatricule}",produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeDto findVehiculeByImmatricule(@PathVariable("immatricule") String immatricule);

    @GetMapping(value = APP_ROOT +"/Vehicules/update/{idVehicule}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeDto updateVehicule(@RequestBody VehiculeDto Vehicule,@PathVariable("idVehicule") Long id);

    @GetMapping(value = APP_ROOT +"/Vehicules/delete/{idVehicule}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteVehicule(@PathVariable("idVehicule") Long id);

    @GetMapping(value = APP_ROOT +"/Vehicules/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<VehiculeDto> getVehicules(@RequestParam("page") int page,@RequestParam("size") int size);


}
