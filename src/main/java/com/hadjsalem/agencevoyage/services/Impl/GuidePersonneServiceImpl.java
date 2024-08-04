package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import com.hadjsalem.agencevoyage.mapper.GuidePersonneMapper;
import com.hadjsalem.agencevoyage.repositories.GuidePersonneRepository;
import com.hadjsalem.agencevoyage.services.GuidePersonneService;
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
public class GuidePersonneServiceImpl implements GuidePersonneService {
    private GuidePersonneRepository guidePersonneRepository;
    private GuidePersonneMapper mapper;


    @Override
    public GuidePersonneDto findGuidePersonneById(Long id) {
    Optional<GuidePersonne> optionalGuidePersonne =guidePersonneRepository.findById(id);
    return optionalGuidePersonne.map(mapper::fromGuidePersonne).orElseThrow(()->new NoSuchElementException("GuidePersonne Not Found"));

    }

    @Override
    public GuidePersonneDto findGuidePersonneByNumTel(Integer NumTel) {
        Optional<GuidePersonne> guidePersonne = guidePersonneRepository.findPersonneByNumTelephone(NumTel);
        if (!guidePersonne.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromGuidePersonne(guidePersonne.get());
    }

    @Override
    public GuidePersonneDto saveGuidePersonne(GuidePersonneDto GuidePersonneDto) {
      GuidePersonne GuidePersonne1 = mapper.fromGuidePersonneDto(GuidePersonneDto);
      GuidePersonne GuidePersonne2= guidePersonneRepository.save(GuidePersonne1);
      return mapper.fromGuidePersonne(GuidePersonne2);
    }

    @Override
    public GuidePersonneDto updateGuidePersonne(GuidePersonneDto GuidePersonnedto, Long id) {
      Optional<GuidePersonne> GuidePersonne1 = guidePersonneRepository.findById(id);
      if(GuidePersonne1.isPresent()){
      GuidePersonne GuidePersonne2 = GuidePersonne1.get();
      GuidePersonne GuidePersonne3= guidePersonneRepository.saveAndFlush(GuidePersonne2);
      return mapper.fromGuidePersonne(GuidePersonne3);
      }else {
          throw  new NoSuchElementException("GuidePersonne NotFound");
      }
    }

    @Override
    public void deleteGuidePersonne(Long id) {
       guidePersonneRepository.deleteById(id);
    }

    public PageResponse<GuidePersonneDto> getGuidePersonnes(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<GuidePersonne> GuidePersonnes = guidePersonneRepository.findAll(pageRequest);
        List<GuidePersonneDto> GuidePersonneList = GuidePersonnes.map(mapper::fromGuidePersonne).getContent();

        return new PageResponse<>(
                GuidePersonneList,
                GuidePersonnes.getNumber(),
                GuidePersonnes.getSize(),
                GuidePersonnes.getTotalElements(),
                GuidePersonnes.getTotalPages(),
                GuidePersonnes.isFirst(),
                GuidePersonnes.isLast()
        );
    }
}
