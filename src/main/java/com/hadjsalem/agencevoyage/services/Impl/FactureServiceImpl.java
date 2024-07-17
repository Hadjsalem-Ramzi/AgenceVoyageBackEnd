package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.FactureDto;
import com.hadjsalem.agencevoyage.entities.Destination;
import com.hadjsalem.agencevoyage.entities.Facture;
import com.hadjsalem.agencevoyage.mapper.FactureMapper;
import com.hadjsalem.agencevoyage.repositories.FactureRepository;
import com.hadjsalem.agencevoyage.services.FactureService;
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
      Facture Facture1 = mapper.fromFactureDto(FactureDto);
      Facture Facture2= factureRepository.save(Facture1);
      return mapper.fromFacture(Facture2);
    }

    @Override
    public FactureDto updateFacture(FactureDto Facturedto, Long id) {
      Optional<Facture> Facture1 = factureRepository.findById(id);
      if(Facture1.isPresent()){
      Facture Facture2 = Facture1.get();
      Facture Facture3= factureRepository.saveAndFlush(Facture2);
      return mapper.fromFacture(Facture3);
      }else {
          throw  new NoSuchElementException("Facture NotFound");
      }
    }

    @Override
    public void deleteFacture(Long id) {
       factureRepository.deleteById(id);
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
