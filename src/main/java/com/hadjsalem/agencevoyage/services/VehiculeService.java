package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.VehiculeDto;

public interface VehiculeService {
    VehiculeDto findVehiculeById(Long id);
    VehiculeDto findVehiculeByImmatricule(String email);
    VehiculeDto saveVehicule( VehiculeDto vehicule);
    VehiculeDto updateVehicule( VehiculeDto vehicule,Long id);
   void deleteVehicule(Long id);

   PageResponse<VehiculeDto> getVehicules(int page,int size);

}
