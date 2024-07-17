package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import com.hadjsalem.agencevoyage.entities.Client;
import com.hadjsalem.agencevoyage.mapper.ChauffeurMapper;
import com.hadjsalem.agencevoyage.repositories.ChauffeurRepository;
import com.hadjsalem.agencevoyage.services.ChauffeurService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ChauffeurServiceImpl implements ChauffeurService {
    private ChauffeurRepository chauffeurRepository;
    private ChauffeurMapper mapper;


    @Override
    public ChauffeurDto findChauffeurById(Long id) {
    Optional<Chauffeur> optionalChauffeur =chauffeurRepository.findById(id);
    return optionalChauffeur.map(mapper::fromChaufeur).orElseThrow(()->new NoSuchElementException("Chauffeur Not Found"));

    }

    @Override
    public ChauffeurDto findChauffeurByNumTelephone(Long NumTel) {
        Optional<Chauffeur> chauffeur = chauffeurRepository.findChauffeurByNumTelephone(NumTel);
        if (!chauffeur.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromChaufeur(chauffeur.get());
    }

    @Override
    public ChauffeurDto saveChauffeur(ChauffeurDto ChauffeurDto) {
      Chauffeur Chauffeur1 = mapper.fromChauffeurDto(ChauffeurDto);
      Chauffeur Chauffeur2= chauffeurRepository.save(Chauffeur1);
      return mapper.fromChaufeur(Chauffeur2);
    }

    @Override
    public ChauffeurDto updateChauffeur(ChauffeurDto Chauffeurdto, Long id) {
      Optional<Chauffeur> Chauffeur1 = chauffeurRepository.findById(id);
      if(Chauffeur1.isPresent()){
      Chauffeur Chauffeur2 = Chauffeur1.get();
      Chauffeur Chauffeur3= chauffeurRepository.saveAndFlush(Chauffeur2);
      return mapper.fromChaufeur(Chauffeur3);
      }else {
          throw  new NoSuchElementException("Chauffeur NotFound");
      }
    }

    @Override
    public void deleteChauffeur(Long id) {
       chauffeurRepository.deleteById(id);
    }
}
