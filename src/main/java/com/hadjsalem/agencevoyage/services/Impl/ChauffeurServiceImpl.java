package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import com.hadjsalem.agencevoyage.mapper.ChauffeurMapper;
import com.hadjsalem.agencevoyage.repositories.ChauffeurRepository;
import com.hadjsalem.agencevoyage.services.ChauffeurService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChauffeurServiceImpl implements ChauffeurService {
    private final ChauffeurRepository chauffeurRepository;
    private final ChauffeurMapper mapper;
    private final ObjectsValidators<Chauffeur> chauffeurValidators;




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
       chauffeurValidators.validate(Chauffeur1);
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



    public PageResponse<ChauffeurDto> getChauffeurs(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Chauffeur> Chauffeurs = chauffeurRepository.findAll(pageRequest);
        List<ChauffeurDto> ChauffeurList = Chauffeurs.map(mapper::fromChaufeur).getContent();

        return new PageResponse<>(
                ChauffeurList,
                Chauffeurs.getNumber(),
                Chauffeurs.getSize(),
                Chauffeurs.getTotalElements(),
                Chauffeurs.getTotalPages(),
                Chauffeurs.isFirst(),
                Chauffeurs.isLast()
        );

}


    public String throwException() {
        throw  new IllegalArgumentException("Some Exception happened");
    }
}
