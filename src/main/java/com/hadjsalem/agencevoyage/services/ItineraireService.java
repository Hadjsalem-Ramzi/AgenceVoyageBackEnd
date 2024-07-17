package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.dtos.ItineraireDto;

public interface ItineraireService {
    ItineraireDto findItineraireById(Long id);
    ItineraireDto findItineraireByLibelle(String libelle);
    ItineraireDto saveItineraire(ItineraireDto itineraire);
    ItineraireDto updateItineraire(ItineraireDto itineraire, Long id);
    void deleteItineraire(Long id);




}
