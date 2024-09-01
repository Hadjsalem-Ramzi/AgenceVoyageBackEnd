package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.GuideApi;
import com.hadjsalem.agencevoyage.dtos.GuideDto;
import com.hadjsalem.agencevoyage.services.Impl.GuideServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GuideController implements GuideApi {

    private  final GuideServiceImpl guideService;


    @Override
    public GuideDto saveGuide(GuideDto Guide) {
        return guideService.saveGuide(Guide);
    }

    @Override
    public GuideDto findGuideById(Long id) {
        return guideService.findGuideById(id);
    }

    @Override
    public GuideDto findGuideByName(String name) {
        return guideService.findGuideByName(name);
    }

    @Override
    public GuideDto updateGuide(GuideDto Guide, Long id) {
        return guideService.updateGuide(Guide, id);
    }

    @Override
    public void deleteGuide(Long id) {
      guideService.deleteGuide(id);
    }

    @Override
    public PageResponse<GuideDto> getGuides(int page, int size) {
        return guideService.getGuides(page, size);
    }
}
