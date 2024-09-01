package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.ChauffeurApi;
import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.services.Impl.ChauffeurServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChauffeurController implements ChauffeurApi {

    private  final ChauffeurServiceImpl chauffeurService;


    @Override
    public ChauffeurDto saveChauffeur(ChauffeurDto chauffeurDto) {
        return chauffeurService.saveChauffeur(chauffeurDto);
    }

    @Override
    public ChauffeurDto findChauffeurById(Long id) {
        return chauffeurService.findChauffeurById(id);
    }

    @Override
    public ChauffeurDto findChauffeurByNumTelephone(Integer NumTelephone) {
        return chauffeurService.findChauffeurByNumTelephone(NumTelephone);
    }

    @Override
    public ChauffeurDto updateChauffeur(ChauffeurDto chauffeurDto, Long id) {
        return chauffeurService.updateChauffeur(chauffeurDto,id);
    }

    @Override
    public void deleteChauffeur(Long id) {
        chauffeurService.deleteChauffeur(id);

    }

    @Override
    public PageResponse<ChauffeurDto> getChauffeurs(int page, int size) {
        return chauffeurService.getChauffeurs(page, size);
    }
}
