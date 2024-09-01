package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.SocieteLocationApi;
import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;
import com.hadjsalem.agencevoyage.services.Impl.SocieteLocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class SocieteLocationController implements SocieteLocationApi {

    private  final SocieteLocationServiceImpl societeLocationService ;


    @Override
    public SocieteLocationDto saveSocieteLocation(SocieteLocationDto SocieteLocation) {
        return societeLocationService.saveSocieteLocation(SocieteLocation) ;
    }

    @Override
    public SocieteLocationDto findSocieteLocationById(Long id) {
        return societeLocationService.findSocieteLocationById(id) ;
    }

    @Override
    public SocieteLocationDto findSocieteLocationByName(String name) {
        return societeLocationService.findSocieteLocationByName(name) ;
    }

    @Override
    public SocieteLocationDto updateSocieteLocation(SocieteLocationDto SocieteLocation, Long id) {
        return societeLocationService.updateSocieteLocation(SocieteLocation, id) ;
    }

    @Override
    public void deleteSocieteLocation(Long id) {
     societeLocationService.deleteSocieteLocation(id) ;
    }

    @Override
    public PageResponse<SocieteLocationDto> getSocieteLocations(int page, int size) {
        return societeLocationService.getSocieteLocations(page, size) ;
    }
}
