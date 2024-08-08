package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePersonneDto;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import com.hadjsalem.agencevoyage.entities.GuidePersonne;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.GuidePersonneMapper;
import com.hadjsalem.agencevoyage.repositories.GuidePersonneRepository;
import com.hadjsalem.agencevoyage.services.GuidePersonneService;
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
public class GuidePersonneServiceImpl implements GuidePersonneService {
    private GuidePersonneRepository guidePersonneRepository;
    private GuidePersonneMapper mapper;
    private ObjectsValidators<GuidePersonne> guidePersonneValidators;

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
    public GuidePersonneDto saveGuidePersonne(GuidePersonneDto guidePersonneDto) {
      GuidePersonne guidePersonne = mapper.fromGuidePersonneDto(guidePersonneDto);
         if( guidePersonne == null){
             throw new IllegalArgumentException("Guide Personne est null");
         }
         guidePersonneValidators.validate(guidePersonne);
         boolean exists= guidePersonneRepository.existsByNumTel(guidePersonneDto.getNumTel());
         if (exists){
             throw  new DuplicateEntryException("Un Guide Personne est existe avec cette Num télephone");
         }
         GuidePersonne savedGuidePersonne = guidePersonneRepository.save(guidePersonne);
         return mapper.fromGuidePersonne(guidePersonne);
    }

    @Override
    public GuidePersonneDto updateGuidePersonne(GuidePersonneDto guidePersonnedto, Long id) {
      Optional<GuidePersonne> guidePersonne1 = guidePersonneRepository.findById(id);
      if(guidePersonne1.isPresent()){
      GuidePersonne guidePersonne2 = guidePersonne1.get();
      guidePersonne2.setId(id);
      guidePersonneValidators.validate(guidePersonne2);
      if(guidePersonneRepository.existsByNumTel(guidePersonne2.getNumTel())){
          throw new DuplicateEntryException("un Guide Personne est existe avec cette Num Télephone");
      }
      GuidePersonne guidePersonne3 =guidePersonneRepository.saveAndFlush(guidePersonne2);
      return mapper.fromGuidePersonne(guidePersonne3);
      }else {
          throw new EntityNotFoundException("Guide Personne Not Found");
      }
    }

    @Override
    public void deleteGuidePersonne(Long id) {
        if(guidePersonneRepository.findById(id).isPresent()){
            guidePersonneRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Guide Personne with this id Not Found");
        }

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
