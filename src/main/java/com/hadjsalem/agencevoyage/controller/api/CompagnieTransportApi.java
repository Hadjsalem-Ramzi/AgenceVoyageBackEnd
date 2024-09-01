package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface CompagnieTransportApi {

    @PostMapping(value = APP_ROOT+"/CompagnieTransports/saveCompagnieTransport",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    CompagnieTransportDto saveCompagnieTransport(@RequestBody  CompagnieTransportDto CompagnieTransport);

    @GetMapping(value = APP_ROOT +"/CompagnieTransports/{idCompagnieTransport}",produces = MediaType.APPLICATION_JSON_VALUE)
    CompagnieTransportDto findCompagnieTransportById( @PathVariable("idCompagnieTransport") Long id);

    @GetMapping(value = APP_ROOT +"/CompagnieTransports/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    CompagnieTransportDto findCompagnieTransportByName(@PathVariable("name") String name);



    @GetMapping(value = APP_ROOT +"/CompagnieTransports/update/{idCompagnieTransport}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    CompagnieTransportDto updateCompagnieTransport(@RequestBody CompagnieTransportDto CompagnieTransport,@PathVariable("idCompagnieTransport") Long id);

    @GetMapping(value = APP_ROOT +"/CompagnieTransports/delete/{idCompagnieTransport}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteCompagnieTransport(@PathVariable("idCompagnieTransport") Long id);

    @GetMapping(value = APP_ROOT +"/CompagnieTransports/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<CompagnieTransportDto> getCompagnieTransports(@RequestParam("page") int page,@RequestParam("size") int size);


}
