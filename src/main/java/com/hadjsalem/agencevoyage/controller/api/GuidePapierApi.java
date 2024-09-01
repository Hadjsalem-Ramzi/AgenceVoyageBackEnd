package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface GuidePapierApi {

    @PostMapping(value = APP_ROOT+"/GuidePapiers/saveGuidePapier",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePapierDto saveGuidePapier(@RequestBody  GuidePapierDto GuidePapier);

    @GetMapping(value = APP_ROOT +"/GuidePapiers/{idGuidePapier}",produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePapierDto findGuidePapierById( @PathVariable("idGuidePapier") Long id);

    @GetMapping(value = APP_ROOT +"/GuidePapiers/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePapierDto findGuidePapierByName(@PathVariable("name") String name);

    @GetMapping(value = APP_ROOT +"/GuidePapiers/update/{idGuidePapier}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePapierDto updateGuidePapier(@RequestBody GuidePapierDto GuidePapier,@PathVariable("idGuidePapier") Long id);

    @GetMapping(value = APP_ROOT +"/GuidePapiers/delete/{idGuidePapier}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteGuidePapier(@PathVariable("idGuidePapier") Long id);

    @GetMapping(value = APP_ROOT +"/GuidePapiers/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<GuidePapierDto> getGuidePapiers(@RequestParam("page") int page,@RequestParam("size") int size);


}
