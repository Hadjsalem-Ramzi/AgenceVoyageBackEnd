package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.DestinationDto;

public interface DestinationService {
    DestinationDto findDestinationById(Long id);
    DestinationDto findDestinationByVille(String ville);
    DestinationDto saveDestination(DestinationDto destination);
    DestinationDto updateDestination(DestinationDto destination,Long id);
    void deleteDestination(Long id);
    PageResponse<DestinationDto> getDestination(int page, int size);


}
