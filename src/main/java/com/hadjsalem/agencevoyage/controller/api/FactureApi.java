package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.FactureDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface FactureApi {

    @PostMapping(value = APP_ROOT+"/Factures/saveFacture",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    FactureDto saveFacture(@RequestBody  FactureDto Facture);

    @GetMapping(value = APP_ROOT +"/Factures/{idFacture}",produces = MediaType.APPLICATION_JSON_VALUE)
    FactureDto findFactureById( @PathVariable("idFacture") Long id);

    @GetMapping(value = APP_ROOT +"/Factures/{designation}",produces = MediaType.APPLICATION_JSON_VALUE)
    FactureDto findFactureByDesignation(@PathVariable("designation") String designation);

    @GetMapping(value = APP_ROOT +"/Factures/update/{idFacture}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    FactureDto updateFacture(@RequestBody FactureDto Facture,@PathVariable("idFacture") Long id);

    @GetMapping(value = APP_ROOT +"/Factures/delete/{idFacture}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteFacture(@PathVariable("idFacture") Long id);

    @GetMapping(value = APP_ROOT +"/Factures/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<FactureDto> getFactures(@RequestParam("page") int page,@RequestParam("size") int size);


}
