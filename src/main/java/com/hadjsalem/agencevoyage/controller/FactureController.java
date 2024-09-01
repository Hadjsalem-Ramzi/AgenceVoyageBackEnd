package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.FactureApi;
import com.hadjsalem.agencevoyage.dtos.FactureDto;
import com.hadjsalem.agencevoyage.services.Impl.FactureServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FactureController implements FactureApi {

    private  final FactureServiceImpl factureService;


    @Override
    public FactureDto saveFacture(FactureDto Facture) {
        return factureService.saveFacture(Facture);
    }

    @Override
    public FactureDto findFactureById(Long id) {
        return factureService.findFactureById(id);
    }

    @Override
    public FactureDto findFactureByDesignation(String designation) {
        return factureService.findFactureByDesignation(designation);
    }

    @Override
    public FactureDto updateFacture(FactureDto Facture, Long id) {
        return factureService.updateFacture(Facture, id);
    }

    @Override
    public void deleteFacture(Long id) {
      factureService.deleteFacture(id);
    }

    @Override
    public PageResponse<FactureDto> getFactures(int page, int size) {
        return factureService.getFactures(page, size);
    }
}
