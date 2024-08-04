package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuideDto;

public interface GuideService {
    GuideDto findGuideById(Long id);
    GuideDto findGuideByNumTel(Integer numTel);
    GuideDto saveGuide(GuideDto guide);
    GuideDto updateGuide(GuideDto guide,Long id);
    void deleteGuide(Long id);
    PageResponse<GuideDto>getGuides(int page,int size);


}
