package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class GuidePersonneMapper {
    private ModelMapper mapper;

    public GuidePapierDto fromGuidePersonne(GuidePersonne guidePersonne){
        return  mapper.map(guidePersonne,GuidePapierDto.class);
    }

    public GuidePersonne fromGuidePersonneDto(GuidePapierDto guidePapierDto){
        return  mapper.map(guidePapierDto,GuidePersonne.class);
    }

}
