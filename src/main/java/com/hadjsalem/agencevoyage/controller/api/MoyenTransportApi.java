package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface MoyenTransportApi {

    @PostMapping(value = APP_ROOT+"/MoyenTransports/saveMoyenTransport",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    MoyenTransportDto saveMoyenTransport(@RequestBody  MoyenTransportDto MoyenTransport);

    @GetMapping(value = APP_ROOT +"/MoyenTransports/{idMoyenTransport}",produces = MediaType.APPLICATION_JSON_VALUE)
    MoyenTransportDto findMoyenTransportById( @PathVariable("idMoyenTransport") Long id);

    @GetMapping(value = APP_ROOT +"/MoyenTransports/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    MoyenTransportDto findMoyenTransportByName(@PathVariable("name") String name);

    @GetMapping(value = APP_ROOT +"/MoyenTransports/update/{idMoyenTransport}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    MoyenTransportDto updateMoyenTransport(@RequestBody MoyenTransportDto MoyenTransport,@PathVariable("idMoyenTransport") Long id);

    @GetMapping(value = APP_ROOT +"/MoyenTransports/delete/{idMoyenTransport}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteMoyenTransport(@PathVariable("idMoyenTransport") Long id);

    @GetMapping(value = APP_ROOT +"/MoyenTransports/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<MoyenTransportDto> getMoyenTransports(@RequestParam("page") int page,@RequestParam("size") int size);


}
