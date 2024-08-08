package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import com.hadjsalem.agencevoyage.entities.Vehicule;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.VehiculeMapper;
import com.hadjsalem.agencevoyage.repositories.VehiculeRepository;
import com.hadjsalem.agencevoyage.services.VehiculeService;
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
public class VehiculeServiceImpl implements VehiculeService {
    private VehiculeRepository vehiculeRepository;
    private VehiculeMapper mapper;
    private ObjectsValidators<Vehicule> vehiculeValidators;

    @Override
    public VehiculeDto findVehiculeById(Long id) {
    Optional<Vehicule> optionalVehicule =vehiculeRepository.findById(id);
    return optionalVehicule.map(mapper::fromVehicule).orElseThrow(()->new NoSuchElementException("Vehicule Not Found"));

    }

    public VehiculeDto findVehiculeByImmatricule(String firstName) {
        Optional<Vehicule> Vehicule = vehiculeRepository.findVehiculeByImmatricule(firstName);
        if (!Vehicule.isPresent()) {
            throw new RuntimeException("Vehicule Not Found");
        }
        return mapper.fromVehicule(Vehicule.get());
    }

    @Override
    public VehiculeDto saveVehicule(VehiculeDto vehiculeDto) {
      Vehicule vehicule = mapper.fromVehiculeDto(vehiculeDto);
      if( vehicule == null){
          throw  new IllegalArgumentException("vehicule est null");
      }
      vehiculeValidators.validate(vehicule);
      boolean exists= vehiculeRepository.existsByImmatricule(vehiculeDto.getImmatricule());
      if(exists) {
          throw new DuplicateEntryException("une vehicule avec cette Immatriculation est existe");
      }
      Vehicule savedVehicule = vehiculeRepository.save(vehicule);
      return  mapper.fromVehicule(savedVehicule);
    }

    @Override
    public VehiculeDto updateVehicule(VehiculeDto vehiculedto, Long id) {
      Optional<Vehicule> vehicule1 = vehiculeRepository.findById(id);
      if(vehicule1.isPresent()){
      Vehicule vehicule2 = vehicule1.get();
      vehicule2.setId(id);
      vehiculeValidators.validate(vehicule2);
      if( vehiculeRepository.existsByImmatricule(vehicule2.getImmatricule())){
          throw new DuplicateEntryException("une vehicule avec cette immatriculation est existe");
      }
       Vehicule vehicule3= vehiculeRepository.saveAndFlush(vehicule2);
      return mapper.fromVehicule(vehicule3);
      }else {
          throw  new NoSuchElementException("Vehicule NotFound");
      }
    }

    @Override
    public void deleteVehicule(Long id) {
        if(vehiculeRepository.findById(id).isPresent()){
            vehiculeRepository.deleteById(id);
        }
         else {
             throw new EntityNotFoundException("vehicule with this id Not Found");
        }
    }

    public PageResponse<VehiculeDto> getVehicules(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Vehicule> vehicules = vehiculeRepository.findAll(pageRequest);
        List<VehiculeDto> VehiculeList = vehicules.map(mapper::fromVehicule).getContent();

        return new PageResponse<>(
                VehiculeList,
                vehicules.getNumber(),
                vehicules.getSize(),
                vehicules.getTotalElements(),
                vehicules.getTotalPages(),
                vehicules.isFirst(),
                vehicules.isLast()
        );
    }
}
