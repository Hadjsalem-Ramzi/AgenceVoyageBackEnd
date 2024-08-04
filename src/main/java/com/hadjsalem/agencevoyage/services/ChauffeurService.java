package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;

public interface ChauffeurService {
    ChauffeurDto findChauffeurById(Long id);
    ChauffeurDto findChauffeurByNumTelephone(Integer NumTelephone);
    ChauffeurDto saveChauffeur(ChauffeurDto chauffeur);
    ChauffeurDto updateChauffeur(ChauffeurDto chauffeur, Long id);
    void deleteChauffeur(Long id);
    PageResponse<ChauffeurDto> getChauffeurs(int page, int size);




}
