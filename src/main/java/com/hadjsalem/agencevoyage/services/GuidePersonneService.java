package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;

public interface GuidePersonneService {
    GuidePersonneDto findGuidePersonneById(Long id);
    GuidePersonneDto  findGuidePersonneByName(String nom);
    GuidePersonneDto  saveGuidePersonne(GuidePersonneDto  guidePersonne);
    GuidePersonneDto  updateGuidePersonne(GuidePersonneDto  guidePersonne,Long id);
    void deleteGuidePersonne(Long id);

    PageResponse<GuidePersonneDto>getGuidePersonnes(int page, int size);

}
