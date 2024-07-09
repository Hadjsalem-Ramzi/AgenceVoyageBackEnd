package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.TerrestreDto;
import com.hadjsalem.agencevoyage.entities.Terrestre;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class TerrestreMapper {
    private ModelMapper mapper;

    public TerrestreDto fromTerrestre(Terrestre terrestre){
        return  mapper.map(terrestre,TerrestreDto.class);
    }

    public Terrestre fromTerrestreDto(TerrestreDto terrestreDto){
        return mapper.map(terrestreDto,Terrestre.class);
    }


}
