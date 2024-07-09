package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.IteneraireDto;
import com.hadjsalem.agencevoyage.entities.Itineraire;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class IteneraireMapper {
    private ModelMapper mapper;

    public IteneraireDto fromIteneraire(Itineraire itineraire){
        return  mapper.map(itineraire,IteneraireDto.class);
    }

    public Itineraire fromIteneraireDto(IteneraireDto iteneraireDto){
        return  mapper.map(iteneraireDto ,Itineraire.class);
    }


}
