package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;

public interface SocieteLocationService {
    SocieteLocationDto findSocieteLocationById(Long id);
    SocieteLocationDto findSocieteLocationByNom(String email);
    SocieteLocationDto saveSocieteLocation( SocieteLocationDto societeLocation);
    SocieteLocationDto updateSocieteLocation( SocieteLocationDto societeLocation,Long id);
    void deleteSocieteLocation(Long id);




}
