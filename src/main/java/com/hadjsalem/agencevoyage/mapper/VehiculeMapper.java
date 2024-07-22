package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import com.hadjsalem.agencevoyage.entities.Vehicule;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VehiculeMapper {
    private ModelMapper mapper;

    @Autowired
    public VehiculeMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }



    public VehiculeDto fromVehicule(Vehicule vehicule){
        return  mapper.map(vehicule,VehiculeDto.class);
    }

    public Vehicule fromVehiculeDto(VehiculeDto vehiculeDto){
        return  mapper.map(vehiculeDto,Vehicule.class);
    }

}
