package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ItineraireDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface ItineraireApi {

    @PostMapping(value = APP_ROOT+"/Itineraires/saveItineraire",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ItineraireDto   saveItineraire(@RequestBody  ItineraireDto Itineraire);

    @GetMapping(value = APP_ROOT +"/Itineraires/{idItineraire}",produces = MediaType.APPLICATION_JSON_VALUE)
    ItineraireDto findItineraireById( @PathVariable("idItineraire") Long id);

    @GetMapping(value = APP_ROOT +"/Itineraires/{libelle}",produces = MediaType.APPLICATION_JSON_VALUE)
    ItineraireDto findItineraireByLibelle(@PathVariable("libelle") String libelle);

    @GetMapping(value = APP_ROOT +"/Itineraires/update/{idItineraire}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ItineraireDto updateItineraire(@RequestBody ItineraireDto Itineraire,@PathVariable("idItineraire") Long id);

    @GetMapping(value = APP_ROOT +"/Itineraires/delete/{idItineraire}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteItineraire(@PathVariable("idItineraire") Long id);

    @GetMapping(value = APP_ROOT +"/Itineraires/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<ItineraireDto> getItineraires(@RequestParam("page") int page,@RequestParam("size") int size);


}
