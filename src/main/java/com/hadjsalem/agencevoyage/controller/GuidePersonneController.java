package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.GuidePersonneApi;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
import com.hadjsalem.agencevoyage.services.Impl.GuidePersonneServiceImpl;
import com.hadjsalem.agencevoyage.services.Impl.GuideServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GuidePersonneController implements GuidePersonneApi {

    private  final GuidePersonneServiceImpl guidePersonneService;


    @Override
    public GuidePersonneDto saveGuidePersonne(GuidePersonneDto GuidePersonne) {
        return guidePersonneService.saveGuidePersonne(GuidePersonne);
    }

    @Override
    public GuidePersonneDto findGuidePersonneById(Long id) {
        return guidePersonneService.findGuidePersonneById(id);
    }

    @Override
    public GuidePersonneDto findGuidePersonneByName(String name) {
        return guidePersonneService.findGuidePersonneByName(name);
    }

    @Override
    public GuidePersonneDto updateGuidePersonne(GuidePersonneDto GuidePersonne, Long id) {
        return guidePersonneService.updateGuidePersonne(GuidePersonne, id);
    }

    @Override
    public void deleteGuidePersonne(Long id) {
     guidePersonneService.deleteGuidePersonne(id);
    }

    @Override
    public PageResponse<GuidePersonneDto> getGuidePersonnes(int page, int size) {
        return guidePersonneService.getGuidePersonnes(page, size);
    }
}
