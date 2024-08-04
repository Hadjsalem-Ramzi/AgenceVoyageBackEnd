package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;

public interface GuidePersonneService {
    GuidePersonneDto findGuidePersonneById(Long id);
    GuidePersonneDto  findGuidePersonneByNumTel(Integer NumTel);
    GuidePersonneDto  saveGuidePersonne(GuidePersonneDto  guidePersonne);
    GuidePersonneDto  updateGuidePersonne(GuidePersonneDto  guidePersonne,Long id);
    void deleteGuidePersonne(Long id);

    PageResponse<GuidePersonneDto>getGuidePersonnes(int page, int size);





}
