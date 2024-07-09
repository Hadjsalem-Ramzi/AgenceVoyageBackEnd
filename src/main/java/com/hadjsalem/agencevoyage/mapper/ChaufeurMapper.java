package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ChaufeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ChaufeurMapper {

    private ModelMapper mapper;

    public ChaufeurDto fromChaufeur(Chauffeur chauffeur){
        return mapper.map(chauffeur,ChaufeurDto.class);
    }

    public  Chauffeur fromChauffeurDto(ChaufeurDto chaufeurDto){
        return mapper.map(chaufeurDto,Chauffeur.class);
    }

}
