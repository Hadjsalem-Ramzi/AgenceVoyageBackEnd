package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuideDto;
import com.hadjsalem.agencevoyage.entities.Guide;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.GuideMapper;
import com.hadjsalem.agencevoyage.repositories.GuideRepository;
import com.hadjsalem.agencevoyage.services.GuideService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GuideServiceImpl implements GuideService {
    private GuideRepository guideRepository;
    private GuideMapper mapper;
    private ObjectsValidators<Guide> guideValidators;

    @Override
    public GuideDto findGuideById(Long id) {
    Optional<Guide> optionalGuide =guideRepository.findById(id);
    return optionalGuide.map(mapper::fromGuide).orElseThrow(()->new EntityNotFoundException("Guide Not Found"));

    }

    @Override
    public GuideDto findGuideByNumTel(Integer numTel) {
        Optional<Guide> guide= guideRepository.findGuideByNumTel(numTel);
        if (!guide.isPresent()) {
            throw new EntityNotFoundException("Guide Not Found");
        }
        return mapper.fromGuide(guide.get());
    }
    @Override
    public GuideDto saveGuide(GuideDto guideDto) {
      Guide guide = mapper.fromGuideDto(guideDto);
      if(guide == null){
          throw new IllegalArgumentException("Guide Not Found");
      }
      guideValidators.validate(guide);
      boolean exists = guideRepository.existsByNumTel(guideDto.getNumTel());
      if( exists){
          throw new DuplicateEntryException(" A Guid was found with this NumTélephone");
      }
      Guide savedGuide = guideRepository.save(guide);
      return mapper.fromGuide(savedGuide);
    }

    @Override
    public GuideDto updateGuide(GuideDto guidedto, Long id) {
      Optional<Guide> guide1 = guideRepository.findById(id);
      if(guide1.isPresent()){
      Guide guide2 = guide1.get();
      guide2.setId(id);
      guideValidators.validate(guide2);
      if(guideRepository.existsByNumTel(guide2.getNumTel())){
          throw new DuplicateEntryException("A Guide was Found with this Télephone");
      }
      Guide guide3 = guideRepository.saveAndFlush(guide2);
      return  mapper.fromGuide(guide3);
      }else {
          throw  new EntityNotFoundException("Guide NotFound");
      }
    }

    @Override
    public void deleteGuide(Long id) {
        if( guideRepository.findById(id).isPresent()){
            guideRepository.deleteById(id);
        }
          else {
              throw new EntityNotFoundException("Guide was Not Found");
        }
    }

    public PageResponse<GuideDto> getGuides(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Guide> Guides = guideRepository.findAll(pageRequest);
        List<GuideDto> GuideList = Guides.map(mapper::fromGuide).getContent();

        return new PageResponse<>(
                GuideList,
                Guides.getNumber(),
                Guides.getSize(),
                Guides.getTotalElements(),
                Guides.getTotalPages(),
                Guides.isFirst(),
                Guides.isLast()
        );
    }
}
