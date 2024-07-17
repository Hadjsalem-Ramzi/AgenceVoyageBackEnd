package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
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

    public GuidePersonneDto fromGuidePersonne(GuidePersonne guidePersonne){
        return  mapper.map(guidePersonne,GuidePersonneDto.class);
    }

    public GuidePersonne fromGuidePersonneDto(GuidePersonneDto guidePersonneDto){
        return  mapper.map(guidePersonneDto,GuidePersonne.class);
    }

}
