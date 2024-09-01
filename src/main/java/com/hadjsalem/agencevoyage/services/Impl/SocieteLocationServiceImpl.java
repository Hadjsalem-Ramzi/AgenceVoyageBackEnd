package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;
import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.SocieteLocationMapper;
import com.hadjsalem.agencevoyage.mapper.SocieteLocationMapper;
import com.hadjsalem.agencevoyage.repositories.SocieteLocationRepository;
import com.hadjsalem.agencevoyage.repositories.SocieteLocationRepository;
import com.hadjsalem.agencevoyage.services.SocieteLocationService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SocieteLocationServiceImpl implements SocieteLocationService {
    private SocieteLocationRepository  societeLocationRepository;
    private SocieteLocationMapper mapper;
    private ObjectsValidators<SocieteLocation> societeLocationValidators;

    @Override
    public SocieteLocationDto findSocieteLocationById(Long id) {
    Optional<SocieteLocation> optionalSocieteLocation =societeLocationRepository.findById(id);
    return optionalSocieteLocation.map(mapper::fromSocieteLocation).orElseThrow(()->new NoSuchElementException("SocieteLocation Not Found"));

    }

    @Override
    public SocieteLocationDto findSocieteLocationByName(String name) {
        Optional<SocieteLocation> SocieteLocation= societeLocationRepository.findSocieteLocationByNom(name);
        if (!SocieteLocation.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromSocieteLocation(SocieteLocation.get());
    }
    @Override
    public SocieteLocationDto saveSocieteLocation(SocieteLocationDto societeLocationDto) {
      SocieteLocation societeLocation = mapper.fromSocieteLocationDto(societeLocationDto);
     if( societeLocation == null){
         throw new IllegalArgumentException("SocieteLocation est null");
     }
     societeLocationValidators.validate(societeLocation);
     boolean exists = societeLocationRepository.existsByNumTel(societeLocation.getNumTel());
     if(exists){
         throw new DuplicateEntryException("un societe Location est existe avec cette NumTélephone");
     }
     SocieteLocation savedSocieteLocation = societeLocationRepository.save(societeLocation);
     return mapper.fromSocieteLocation(savedSocieteLocation);
    }

    @Override
    public SocieteLocationDto updateSocieteLocation(SocieteLocationDto societeLocationdto, Long id) {
        Optional<SocieteLocation> societeLocation1 = societeLocationRepository.findById(id);
        if (societeLocation1.isPresent()) {
            SocieteLocation societeLocation2 = societeLocation1.get();
            societeLocation2.setId(id);
            societeLocationValidators.validate(societeLocation2);
            if(societeLocationRepository.existsByNumTel(societeLocation2.getNumTel())){
                throw  new DuplicateEntryException("un chauffeur avec ce numéro de télephone existe déjà");
            }
            SocieteLocation societeLocation3 = societeLocationRepository.saveAndFlush(societeLocation2);
            return mapper.fromSocieteLocation(societeLocation3);
        } else {
            throw  new EntityNotFoundException("societeLocation Not Found");
        }
    }

    @Override
    public void deleteSocieteLocation(Long id) {
        if(societeLocationRepository.findById(id).isPresent()){
            societeLocationRepository.deleteById(id);
        } else {
            throw  new EntityNotFoundException("societeLocation with this id Not Found");
        }

    }

    public PageResponse<SocieteLocationDto> getSocieteLocations(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<SocieteLocation> SocieteLocations = societeLocationRepository.findAll(pageRequest);
        List<SocieteLocationDto> SocieteLocationList = SocieteLocations.map(mapper::fromSocieteLocation).getContent();

        return new PageResponse<>(
                SocieteLocationList,
                SocieteLocations.getNumber(),
                SocieteLocations.getSize(),
                SocieteLocations.getTotalElements(),
                SocieteLocations.getTotalPages(),
                SocieteLocations.isFirst(),
                SocieteLocations.isLast()
        );
    }
}
