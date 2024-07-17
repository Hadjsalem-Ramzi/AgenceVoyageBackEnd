package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ItineraireDto;
import com.hadjsalem.agencevoyage.entities.Itineraire;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ItineraireMapper {
    private ModelMapper mapper;

    public ItineraireDto fromItineraire(Itineraire itineraire){
        return  mapper.map(itineraire, ItineraireDto.class);
    }

    public Itineraire fromItineraireDto(ItineraireDto iteneraireDto){
        return  mapper.map(iteneraireDto ,Itineraire.class);
    }


}
