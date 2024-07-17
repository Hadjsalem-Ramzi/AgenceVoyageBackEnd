package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.SocieteLocationDto;
import com.hadjsalem.agencevoyage.entities.SocieteLocation;
import com.hadjsalem.agencevoyage.mapper.SocieteLocationMapper;
import com.hadjsalem.agencevoyage.mapper.SocieteLocationMapper;
import com.hadjsalem.agencevoyage.repositories.SocieteLocationRepository;
import com.hadjsalem.agencevoyage.repositories.SocieteLocationRepository;
import com.hadjsalem.agencevoyage.services.SocieteLocationService;
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


    @Override
    public SocieteLocationDto findSocieteLocationById(Long id) {
    Optional<SocieteLocation> optionalSocieteLocation =societeLocationRepository.findById(id);
    return optionalSocieteLocation.map(mapper::fromSocieteLocation).orElseThrow(()->new NoSuchElementException("SocieteLocation Not Found"));

    }

    @Override
    public SocieteLocationDto findSocieteLocationByNom(String nom) {
        Optional<SocieteLocation> SocieteLocation= societeLocationRepository.findSocieteLocationByNom(nom);
        if (!SocieteLocation.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromSocieteLocation(SocieteLocation.get());
    }
    @Override
    public SocieteLocationDto saveSocieteLocation(SocieteLocationDto SocieteLocationDto) {
      SocieteLocation SocieteLocation1 = mapper.fromSocieteLocationDto(SocieteLocationDto);
      SocieteLocation SocieteLocation2= societeLocationRepository.save(SocieteLocation1);
      return mapper.fromSocieteLocation(SocieteLocation2);
    }

    @Override
    public SocieteLocationDto updateSocieteLocation(SocieteLocationDto SocieteLocationdto, Long id) {
      Optional<SocieteLocation> SocieteLocation1 = societeLocationRepository.findById(id);
      if(SocieteLocation1.isPresent()){
      SocieteLocation SocieteLocation2 = SocieteLocation1.get();
      SocieteLocation SocieteLocation3= societeLocationRepository.saveAndFlush(SocieteLocation2);
      return mapper.fromSocieteLocation(SocieteLocation3);
      }else {
          throw  new NoSuchElementException("SocieteLocation NotFound");
      }
    }

    @Override
    public void deleteSocieteLocation(Long id) {
       societeLocationRepository.deleteById(id);
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
