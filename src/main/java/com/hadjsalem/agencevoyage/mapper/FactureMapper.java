package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.FactureDto;
import com.hadjsalem.agencevoyage.entities.Facture;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FactureMapper {
    private ModelMapper mapper;

    @Autowired
    public FactureMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public FactureDto fromFacture(Facture facture){
        return  mapper.map(facture,FactureDto.class);
    }

    public  Facture fromFactureDto(FactureDto factureDto){
        return mapper.map(factureDto,Facture.class);
    }

}
