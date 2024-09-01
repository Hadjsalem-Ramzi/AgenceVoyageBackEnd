package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface TransportCommunApi {

    @PostMapping(value = APP_ROOT+"/TransportCommuns/saveTransportCommun",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    TransportCommunDto saveTransportCommun(@RequestBody  TransportCommunDto TransportCommun);

    @GetMapping(value = APP_ROOT +"/TransportCommuns/{idTransportCommun}",produces = MediaType.APPLICATION_JSON_VALUE)
    TransportCommunDto findTransportCommunById( @PathVariable("idTransportCommun") Long id);

    @GetMapping(value = APP_ROOT +"/TransportCommuns/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    TransportCommunDto findTransportCommunByName(@PathVariable("name") String name);

    @GetMapping(value = APP_ROOT +"/TransportCommuns/update/{idTransportCommun}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    TransportCommunDto updateTransportCommun(@RequestBody TransportCommunDto TransportCommun,@PathVariable("idTransportCommun") Long id);

    @GetMapping(value = APP_ROOT +"/TransportCommuns/delete/{idTransportCommun}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteTransportCommun(@PathVariable("idTransportCommun") Long id);

    @GetMapping(value = APP_ROOT +"/TransportCommuns/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<TransportCommunDto> getTransportCommuns(@RequestParam("page") int page,@RequestParam("size") int size);


}
