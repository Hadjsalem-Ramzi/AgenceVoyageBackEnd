package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.FactureDto;
import com.hadjsalem.agencevoyage.entities.Facture;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class FactureMapper {
    private ModelMapper mapper;

    public FactureDto fromFacture(Facture facture){
        return  mapper.map(facture,FactureDto.class);
    }

    public  Facture fromFactureDto(FactureDto factureDto){
        return mapper.map(factureDto,Facture.class);
    }

}
