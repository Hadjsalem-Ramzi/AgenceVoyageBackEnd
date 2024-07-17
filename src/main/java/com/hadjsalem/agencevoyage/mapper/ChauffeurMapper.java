package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ChauffeurMapper {

    private ModelMapper mapper;

    public ChauffeurDto fromChaufeur(Chauffeur chauffeur){
        return mapper.map(chauffeur, ChauffeurDto.class);
    }

    public  Chauffeur fromChauffeurDto(ChauffeurDto chaufeurDto){
        return mapper.map(chaufeurDto,Chauffeur.class);
    }

}
