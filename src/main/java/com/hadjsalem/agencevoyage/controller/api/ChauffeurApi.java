package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface ChauffeurApi {

    @PostMapping(value = APP_ROOT+"/chauffeurs/saveChaufeur",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ChauffeurDto saveChauffeur(@RequestBody  ChauffeurDto chauffeur);

    @GetMapping(value = APP_ROOT +"/chauffeurs/{idChauffeur}",produces = MediaType.APPLICATION_JSON_VALUE)
    ChauffeurDto findChauffeurById( @PathVariable("idChauffeur") Long id);

    @GetMapping(value = APP_ROOT +"/chauffeurs/{NumTel}",produces = MediaType.APPLICATION_JSON_VALUE)
    ChauffeurDto findChauffeurByNumTelephone(@PathVariable("NumTel") Integer NumTelephone);

    @GetMapping(value = APP_ROOT +"/chauffeurs/update/{idChauffeur}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ChauffeurDto updateChauffeur(@RequestBody ChauffeurDto chauffeur,@PathVariable("idChauffeur") Long id);

    @GetMapping(value = APP_ROOT +"/chauffeurs/delete/{idChauffeur}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteChauffeur(@PathVariable("idChauffeur") Long id);

    @GetMapping(value = APP_ROOT +"/chauffeurs/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<ChauffeurDto> getChauffeurs(@RequestParam("page") int page,@RequestParam("size") int size);


}
