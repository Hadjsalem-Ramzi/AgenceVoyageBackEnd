package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.dtos.VehiculeDto;
import com.hadjsalem.agencevoyage.entities.Vehicule;
import com.hadjsalem.agencevoyage.mapper.VehiculeMapper;
import com.hadjsalem.agencevoyage.repositories.VehiculeRepository;
import com.hadjsalem.agencevoyage.services.VehiculeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {
    private VehiculeRepository vehiculeRepository;
    private VehiculeMapper mapper;


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
    public VehiculeDto saveVehicule(VehiculeDto VehiculeDto) {
      Vehicule Vehicule1 = mapper.fromVehiculeDto(VehiculeDto);
      Vehicule Vehicule2= vehiculeRepository.save(Vehicule1);
      return mapper.fromVehicule(Vehicule2);
    }

    @Override
    public VehiculeDto updateVehicule(VehiculeDto Vehiculedto, Long id) {
      Optional<Vehicule> Vehicule1 = vehiculeRepository.findById(id);
      if(Vehicule1.isPresent()){
      Vehicule Vehicule2 = Vehicule1.get();
      Vehicule Vehicule3= vehiculeRepository.saveAndFlush(Vehicule2);
      return mapper.fromVehicule(Vehicule3);
      }else {
          throw  new NoSuchElementException("Vehicule NotFound");
      }
    }

    @Override
    public void deleteVehicule(Long id) {
       vehiculeRepository.deleteById(id);
    }
}
