package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import com.hadjsalem.agencevoyage.entities.Vehicule;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class VehiculeMapper {
    private ModelMapper mapper;

    public VehiculeDto fromVehicule(Vehicule vehicule){
        return  mapper.map(vehicule,VehiculeDto.class);
    }

    public Vehicule fromVehiculeDto(VehiculeDto vehiculeDto){
        return  mapper.map(vehiculeDto,Vehicule.class);
    }

}
