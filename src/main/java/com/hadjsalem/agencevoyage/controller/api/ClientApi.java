package com.hadjsalem.agencevoyage.controller.api;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ClientDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface ClientApi {

    @PostMapping(value = APP_ROOT+"/clients/saveClient",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto saveclient(@RequestBody  ClientDto client);

    @GetMapping(value = APP_ROOT +"/clients/{idclient}",produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findclientById( @PathVariable("idclient") Long id);

    @GetMapping(value = APP_ROOT +"/clients/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findClientByEmail(@PathVariable("email") String email);

    @GetMapping(value = APP_ROOT +"/clients/update/{idclient}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto updateclient(@RequestBody ClientDto client,@PathVariable("idclient") Long id);

    @GetMapping(value = APP_ROOT +"/clients/delete/{idclient}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteclient(@PathVariable("idclient") Long id);

    @GetMapping(value = APP_ROOT +"/clients/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<ClientDto> getclients(@RequestParam("page") int page,@RequestParam("size") int size);


}
