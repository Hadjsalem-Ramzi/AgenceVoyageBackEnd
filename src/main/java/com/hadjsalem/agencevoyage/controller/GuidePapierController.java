package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.GuidePapierApi;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.services.Impl.GuidePapierServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GuidePapierController implements GuidePapierApi {

    private  final GuidePapierServiceImpl guidePapierService;


    @Override
    public GuidePapierDto saveGuidePapier(GuidePapierDto GuidePapier) {
        return guidePapierService.saveGuidePapier(GuidePapier);
    }

    @Override
    public GuidePapierDto findGuidePapierById(Long id) {
        return guidePapierService.findGuidePapierById(id);
    }

    @Override
    public GuidePapierDto findGuidePapierByName(String name) {
        return guidePapierService.findGuidePapierByName(name);
    }

    @Override
    public GuidePapierDto updateGuidePapier(GuidePapierDto GuidePapier, Long id) {
        return guidePapierService.updateGuidePapier(GuidePapier, id);
    }

    @Override
    public void deleteGuidePapier(Long id) {
      guidePapierService.deleteGuidePapier(id);
    }

    @Override
    public PageResponse<GuidePapierDto> getGuidePapiers(int page, int size) {
        return guidePapierService.getGuidePapiers(page, size);
    }
}
