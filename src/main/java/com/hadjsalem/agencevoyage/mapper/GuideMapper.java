package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.GuideDto;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.Guide;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class GuideMapper {
    private ModelMapper mapper;

    public GuideDto fromGuide(Guide guide){
        return  mapper.map(guide,GuideDto.class);
    }

    public Guide fromGuideDto(GuideDto guideDto){
        return mapper.map(guideDto,Guide.class);
    }
}

