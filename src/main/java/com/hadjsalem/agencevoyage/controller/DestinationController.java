package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.DestinationApi;
import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import com.hadjsalem.agencevoyage.services.Impl.DestinationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DestinationController implements DestinationApi {

    private  final DestinationServiceImpl destinationService;


    @Override
    public DestinationDto saveDestination(DestinationDto Destination) {
        return destinationService.saveDestination(Destination);
    }

    @Override
    public DestinationDto findDestinationById(Long id) {
        return destinationService.findDestinationById(id);
    }

    @Override
    public DestinationDto findDestinationByVille(String ville) {
        return destinationService.findDestinationByVille(ville);
    }

    @Override
    public DestinationDto updateDestination(DestinationDto Destination, Long id) {
        return destinationService.updateDestination(Destination, id);
    }

    @Override
    public void deleteDestination(Long id) {
      destinationService.deleteDestination(id);
    }

    @Override
    public PageResponse<DestinationDto> getDestinations(int page, int size) {
        return destinationService.getDestination(page, size);
    }
}
