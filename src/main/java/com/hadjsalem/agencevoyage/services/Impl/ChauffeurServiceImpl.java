package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.ChauffeurMapper;
import com.hadjsalem.agencevoyage.repositories.ChauffeurRepository;
import com.hadjsalem.agencevoyage.services.ChauffeurService;
import com.hadjsalem.agencevoyage.validators.ObjectsValidators;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class ChauffeurServiceImpl implements ChauffeurService {

    private  ChauffeurRepository chauffeurRepository;

    private  ChauffeurMapper mapper;

    private  ObjectsValidators<Chauffeur> chauffeurValidators;

    @Autowired
    public ChauffeurServiceImpl(ChauffeurRepository chauffeurRepository, ChauffeurMapper mapper, ObjectsValidators<Chauffeur> chauffeurValidators) {
        this.chauffeurRepository = chauffeurRepository;
        this.mapper = mapper;
        this.chauffeurValidators = chauffeurValidators;
    }

    @Override
    public ChauffeurDto findChauffeurById(Long id)  {
    Optional<Chauffeur> optionalChauffeur =chauffeurRepository.findById(id);
    return optionalChauffeur.map(mapper::fromChaufeur).orElseThrow(()->new EntityNotFoundException("Chauffeur Not Found"));

    }

    @Override
    public ChauffeurDto findChauffeurByNumTelephone(Integer NumTel) {
        Optional<Chauffeur> chauffeur = chauffeurRepository.findChauffeurByNumTelephone(NumTel);
        if (chauffeur.isEmpty()) {
            throw new EntityNotFoundException("Client Not Found");
        }
        return mapper.fromChaufeur(chauffeur.get());
    }

    @Override
    public ChauffeurDto saveChauffeur(ChauffeurDto chauffeurDto) {
        Chauffeur chauffeur = mapper.fromChauffeurDto(chauffeurDto);
        if (chauffeur == null) {
            log.error("Le chauffeur est nul après la conversion depuis le DTO");
            throw new IllegalArgumentException("Chauffeur est nul");
        }

        // Validation des champs de l'entité
        chauffeurValidators.validate(chauffeur);

        // Vérification de l'existence
        boolean exists = chauffeurRepository.existsByNumTelephone(chauffeurDto.getNumTelephone());
        log.info("Vérification de l'existence: {}", exists);
        if (exists) {
            log.warn("Un chauffeur avec le numéro de téléphone {} existe déjà", chauffeurDto.getNumTelephone());
            throw new DuplicateEntryException("Un chauffeur avec ce numéro de téléphone existe déjà");
        }

        // Sauvegarde et retour
        Chauffeur savedChauffeur = chauffeurRepository.save(chauffeur);
        log.info("Chauffeur enregistré avec succès: {}", savedChauffeur);

        return mapper.fromChaufeur(savedChauffeur);
    }



    @Override
    public ChauffeurDto updateChauffeur(ChauffeurDto Chauffeurdto, Long id) {
      Optional<Chauffeur> chauffeur1 = chauffeurRepository.findById(id);
      if(chauffeur1.isPresent()){
      Chauffeur chauffeur2 = chauffeur1.get();
      chauffeur2.setId(id);
      chauffeurValidators.validate(chauffeur2);
      if(chauffeurRepository.existsByNumTelephone(chauffeur2.getNumTelephone())){
          throw  new DuplicateEntryException("un chauffeur avec ce numéro de télephone existe déjà");
      }
      Chauffeur chauffeur3= chauffeurRepository.saveAndFlush(chauffeur2);
      return mapper.fromChaufeur(chauffeur3);
      }else {
          throw  new EntityNotFoundException("Chauffeur NotFound");
      }
    }

    @Override
    public void deleteChauffeur(Long id) {
        if (chauffeurRepository.findById(id).isPresent()) {
            chauffeurRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("chauffeur with this id not Found");
        }
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



}
