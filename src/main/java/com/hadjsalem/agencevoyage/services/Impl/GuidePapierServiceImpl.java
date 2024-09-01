package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.Facture;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.GuidePapierMapper;
import com.hadjsalem.agencevoyage.repositories.GuidePapierRepository;
import com.hadjsalem.agencevoyage.services.GuidePapierService;
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
public class GuidePapierServiceImpl implements GuidePapierService {
    private GuidePapierRepository guidePapierRepository;
    private GuidePapierMapper mapper;
    private ObjectsValidators<GuidePapier> guidePapierValidators;

    @Override
    public GuidePapierDto findGuidePapierById(Long id) {
    Optional<GuidePapier> optionalGuidePapier =guidePapierRepository.findById(id);
    return optionalGuidePapier.map(mapper::fromGuidePapier).orElseThrow(()->new EntityNotFoundException("GuidePapier Not Found"));

    }

    @Override
    public GuidePapierDto findGuidePapierByName(String name) {

        Optional<GuidePapier> guidePapier = guidePapierRepository.findGuidePapierByName(name);
        if (!guidePapier.isPresent()) {
            throw new RuntimeException("GuidePapier Not Found");
        }
        return mapper.fromGuidePapier(guidePapier.get());
    }

    @Override
    public GuidePapierDto saveGuidePapier(GuidePapierDto guidePapierDto) {
      GuidePapier guidePapier = mapper.fromGuidePapierDto(guidePapierDto);
       if(guidePapier == null){
           throw new IllegalArgumentException("guide Papier Not Found");
       }
       guidePapierValidators.validate(guidePapier);
       boolean exists = guidePapierRepository.existsByName(guidePapierDto.getName());
       if(exists){
           throw new DuplicateEntryException("un Guide Papier est existe avec Cette Libelle");
       }
       GuidePapier savedGuidePapier= guidePapierRepository.save(guidePapier);
       return mapper.fromGuidePapier(savedGuidePapier);
    }

    @Override
    public GuidePapierDto updateGuidePapier(GuidePapierDto guidePapierDto, Long id) {
      Optional<GuidePapier> guidePapier1 = guidePapierRepository.findById(id);
      if(guidePapier1.isPresent()) {
          GuidePapier guidePapier2 = guidePapier1.get();
          guidePapier2.setId(id);
          guidePapierValidators.validate(guidePapier2);
          if (guidePapierRepository.existsByName(guidePapier2.getName())) {
              throw new DuplicateEntryException("Un guide Papier was found with this Libelle");
          }
          GuidePapier guidePapier3 = guidePapierRepository.saveAndFlush(guidePapier2);
          return mapper.fromGuidePapier(guidePapier3);
          }

          else{
              throw new EntityNotFoundException("Guide Papier Not Found");
          }
    }

    @Override
    public void deleteGuidePapier(Long id) {

        if(guidePapierRepository.findById(id).isPresent()){
            guidePapierRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Guide Papier with this id Not Found");
        }

    }

    public PageResponse<GuidePapierDto> getGuidePapiers(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<GuidePapier> GuidePapiers = guidePapierRepository.findAll(pageRequest);
        List<GuidePapierDto> GuidePapierList = GuidePapiers.map(mapper::fromGuidePapier).getContent();

        return new PageResponse<>(
                GuidePapierList,
                GuidePapiers.getNumber(),
                GuidePapiers.getSize(),
                GuidePapiers.getTotalElements(),
                GuidePapiers.getTotalPages(),
                GuidePapiers.isFirst(),
                GuidePapiers.isLast()
        );
    }
}
