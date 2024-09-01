package com.hadjsalem.agencevoyage.controller.api;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuideDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*; 
import static com.hadjsalem.agencevoyage.utils.Constants.APP_ROOT;

public interface GuideApi {

    @PostMapping(value = APP_ROOT+"/Guides/saveGuide",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    GuideDto saveGuide(@RequestBody  GuideDto Guide);

    @GetMapping(value = APP_ROOT +"/Guides/{idGuide}",produces = MediaType.APPLICATION_JSON_VALUE)
    GuideDto findGuideById( @PathVariable("idGuide") Long id);

    @GetMapping(value = APP_ROOT +"/Guides/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    GuideDto findGuideByName(@PathVariable("name") String name);

    @GetMapping(value = APP_ROOT +"/Guides/update/{idGuide}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    GuideDto updateGuide(@RequestBody GuideDto Guide,@PathVariable("idGuide") Long id);

    @GetMapping(value = APP_ROOT +"/Guides/delete/{idGuide}",produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteGuide(@PathVariable("idGuide") Long id);

    @GetMapping(value = APP_ROOT +"/Guides/all",produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<GuideDto> getGuides(@RequestParam("page") int page,@RequestParam("size") int size);


}
