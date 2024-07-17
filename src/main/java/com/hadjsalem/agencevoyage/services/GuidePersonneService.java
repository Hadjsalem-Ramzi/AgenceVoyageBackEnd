package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;

public interface GuidePersonneService {
    GuidePersonneDto findGuidePersonneById(Long id);
    GuidePersonneDto  findGuidePersonneByNumTel(Integer NumTel);
    GuidePersonneDto  saveGuidePersonne(GuidePersonneDto  guidePersonne);
    GuidePersonneDto  updateGuidePersonne(GuidePersonneDto  guidePersonne,Long id);
    void deleteGuidePersonne(Long id);




}
