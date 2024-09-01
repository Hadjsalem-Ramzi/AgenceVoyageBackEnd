package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.VehiculeApi;
import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import com.hadjsalem.agencevoyage.services.Impl.VehiculeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VehiculeController implements VehiculeApi {

    private  final VehiculeServiceImpl vehiculeService ;


    @Override
    public VehiculeDto saveVehicule(VehiculeDto Vehicule) {
        return vehiculeService.saveVehicule(Vehicule) ;
    }

    @Override
    public VehiculeDto findVehiculeById(Long id) {
        return vehiculeService.findVehiculeById(id) ;
    }

    @Override
    public VehiculeDto findVehiculeByImmatricule(String immatricule) {
        return vehiculeService.findVehiculeByImmatricule(immatricule);
    }

    @Override
    public VehiculeDto updateVehicule(VehiculeDto Vehicule, Long id) {
        return vehiculeService.updateVehicule(Vehicule, id) ;
    }

    @Override
    public void deleteVehicule(Long id) {
     vehiculeService.deleteVehicule(id);
    }

    @Override
    public PageResponse<VehiculeDto> getVehicules(int page, int size) {
        return vehiculeService.getVehicules(page, size);
    }
}
