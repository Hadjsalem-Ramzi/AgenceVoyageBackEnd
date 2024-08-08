package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.FactureDto;
import com.hadjsalem.agencevoyage.entities.Destination;
import com.hadjsalem.agencevoyage.entities.Facture;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.FactureMapper;
import com.hadjsalem.agencevoyage.repositories.FactureRepository;
import com.hadjsalem.agencevoyage.services.FactureService;
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
public class FactureServiceImpl implements FactureService {
    private FactureRepository factureRepository;
    private FactureMapper mapper;
    private ObjectsValidators<Facture> factureValidators;

    @Override
    public FactureDto findFactureById(Long id) {
    Optional<Facture> optionalFacture =factureRepository.findById(id);
    return optionalFacture.map(mapper::fromFacture).orElseThrow(()->new NoSuchElementException("Facture Not Found"));

    }

    @Override
    public FactureDto findFactureByDesignation(String designation) {
        Optional<Facture> facture = factureRepository.findFactureByDesignation(designation);
        if (!facture.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromFacture(facture.get());
    }

    @Override
    public FactureDto saveFacture(FactureDto FactureDto) {
      Facture facture = mapper.fromFactureDto(FactureDto);
      if(facture == null){
          throw new IllegalArgumentException("Facture est null");
      }
       factureValidators.validate(facture);
      boolean exists = factureRepository.existsByDesignation(facture.getDesignation());
       if( exists){
           throw new DuplicateEntryException("une Facture est existe avec Cette designation");
       }
       Facture savedFacture = factureRepository.save(facture);
       return mapper.fromFacture(savedFacture);
    }

    @Override
    public FactureDto updateFacture(FactureDto Facturedto, Long id) {
        Optional<Facture> facture1 = factureRepository.findById(id);
        if (facture1.isPresent()) {
            Facture facture2 = facture1.get();
            facture2.setId(id);
            factureValidators.validate(facture2);
            if (factureRepository.existsByDesignation(facture2.getDesignation())) {
                throw new DuplicateEntryException("une Facture est existe avec Cette designation");
            }
            Facture facture3 = factureRepository.saveAndFlush(facture2);
            return mapper.fromFacture(facture3);
        } else {
            throw new EntityNotFoundException("Facture Not Found");
        }
    }
    @Override
    public void deleteFacture(Long id) {
        if(factureRepository.findById(id).isPresent()){
            factureRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("facture with this id Not Found");
        }

    }

    public PageResponse<FactureDto> getFactures(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Facture> Factures = factureRepository.findAll(pageRequest);
        List<FactureDto> FactureList = Factures.map(mapper::fromFacture).getContent();

        return new PageResponse<>(
                FactureList,
                Factures.getNumber(),
                Factures.getSize(),
                Factures.getTotalElements(),
                Factures.getTotalPages(),
                Factures.isFirst(),
                Factures.isLast()
        );
    }
}
