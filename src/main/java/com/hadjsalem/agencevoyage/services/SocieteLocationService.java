package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;

public interface SocieteLocationService {
    SocieteLocationDto findSocieteLocationById(Long id);
    SocieteLocationDto findSocieteLocationByName(String name);
    SocieteLocationDto saveSocieteLocation( SocieteLocationDto societeLocation);
    SocieteLocationDto updateSocieteLocation( SocieteLocationDto societeLocation,Long id);
    void deleteSocieteLocation(Long id);

   PageResponse<SocieteLocationDto> getSocieteLocations(int page,int size);


}
