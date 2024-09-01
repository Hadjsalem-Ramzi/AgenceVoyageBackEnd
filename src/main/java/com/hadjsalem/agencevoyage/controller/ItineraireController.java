package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.ItineraireApi;
import com.hadjsalem.agencevoyage.dtos.ItineraireDto;
import com.hadjsalem.agencevoyage.services.Impl.ItineraireServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItineraireController implements ItineraireApi {

    private final ItineraireServiceImpl itineraireService;

    @Override
    public ItineraireDto saveItineraire(ItineraireDto Itineraire) {
        return itineraireService.saveItineraire(Itineraire);
    }

    @Override
    public ItineraireDto findItineraireById(Long id) {
        return itineraireService.findItineraireById(id);
    }

    @Override
    public ItineraireDto findItineraireByLibelle(String libelle) {
        return itineraireService.findItineraireByLibelle(libelle);
    }

    @Override
    public ItineraireDto updateItineraire(ItineraireDto Itineraire, Long id) {
        return itineraireService.updateItineraire(Itineraire, id);
    }

    @Override
    public void deleteItineraire(Long id) {
      itineraireService.deleteItineraire(id);
    }

    @Override
    public PageResponse<ItineraireDto> getItineraires(int page, int size) {
        return itineraireService.getItineraires(page, size);
    }
}
