package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.dtos.GuideDto;
import com.hadjsalem.agencevoyage.entities.Guide;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import com.hadjsalem.agencevoyage.mapper.GuideMapper;
import com.hadjsalem.agencevoyage.repositories.GuideRepository;
import com.hadjsalem.agencevoyage.services.GuideService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GuideServiceImpl implements GuideService {
    private GuideRepository guideRepository;
    private GuideMapper mapper;


    @Override
    public GuideDto findGuideById(Long id) {
    Optional<Guide> optionalGuide =guideRepository.findById(id);
    return optionalGuide.map(mapper::fromGuide).orElseThrow(()->new NoSuchElementException("Guide Not Found"));

    }

    @Override
    public GuideDto findGuideByNumTel(Integer numTel) {
        Optional<Guide> guide= guideRepository.findGuideByNumTel(numTel);
        if (!guide.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromGuide(guide.get());
    }
    @Override
    public GuideDto saveGuide(GuideDto GuideDto) {
      Guide Guide1 = mapper.fromGuideDto(GuideDto);
      Guide Guide2= guideRepository.save(Guide1);
      return mapper.fromGuide(Guide2);
    }

    @Override
    public GuideDto updateGuide(GuideDto Guidedto, Long id) {
      Optional<Guide> Guide1 = guideRepository.findById(id);
      if(Guide1.isPresent()){
      Guide Guide2 = Guide1.get();
      Guide Guide3= guideRepository.saveAndFlush(Guide2);
      return mapper.fromGuide(Guide3);
      }else {
          throw  new NoSuchElementException("Guide NotFound");
      }
    }

    @Override
    public void deleteGuide(Long id) {
       guideRepository.deleteById(id);
    }
}
