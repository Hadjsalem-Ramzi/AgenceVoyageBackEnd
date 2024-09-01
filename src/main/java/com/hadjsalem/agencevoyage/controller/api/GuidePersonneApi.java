package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface GuidePersonneApi {

    @PostMapping(value = APP_ROOT+"/GuidePersonnes/saveGuidePersonne",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePersonneDto saveGuidePersonne(@RequestBody  GuidePersonneDto GuidePersonne);

    @GetMapping(value = APP_ROOT +"/GuidePersonnes/{idGuidePersonne}",produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePersonneDto findGuidePersonneById( @PathVariable("idGuidePersonne") Long id);

    @GetMapping(value = APP_ROOT +"/GuidePersonnes/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePersonneDto findGuidePersonneByName(@PathVariable("name") String name);

    @GetMapping(value = APP_ROOT +"/GuidePersonnes/update/{idGuidePersonne}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    GuidePersonneDto updateGuidePersonne(@RequestBody GuidePersonneDto GuidePersonne,@PathVariable("idGuidePersonne") Long id);

    @GetMapping(value = APP_ROOT +"/GuidePersonnes/delete/{idGuidePersonne}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteGuidePersonne(@PathVariable("idGuidePersonne") Long id);

    @GetMapping(value = APP_ROOT +"/GuidePersonnes/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<GuidePersonneDto> getGuidePersonnes(@RequestParam("page") int page,@RequestParam("size") int size);


}
