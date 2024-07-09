package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.MaritimeDto;
import com.hadjsalem.agencevoyage.entities.Maritime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class MaritimeMapper {
    private ModelMapper mapper;

    public MaritimeDto fromMaritime(Maritime maritime){
        return  mapper.map(maritime,MaritimeDto.class);
    }

    public Maritime fromMaritiemDto(MaritimeDto maritimeDto){
        return  mapper.map(maritimeDto,Maritime.class);
    }

}
