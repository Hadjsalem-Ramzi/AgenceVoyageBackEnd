package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ItineraireDto;
import com.hadjsalem.agencevoyage.entities.Hotel;
import com.hadjsalem.agencevoyage.entities.Itineraire;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.ItineraireMapper;
import com.hadjsalem.agencevoyage.repositories.ItineraireRepository;
import com.hadjsalem.agencevoyage.services.ItineraireService;
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
public class ItineraireServiceImpl implements ItineraireService {
    private ItineraireRepository itineraireRepository;
    private ItineraireMapper mapper;
    private ObjectsValidators<Itineraire> itineraireValidators;


    @Override
    public ItineraireDto findItineraireById(Long id) {
    Optional<Itineraire> optionalItineraire =itineraireRepository.findById(id);
    return optionalItineraire.map(mapper::fromItineraire).orElseThrow(()->new NoSuchElementException("Itineraire Not Found"));

    }

    @Override
    public ItineraireDto findItineraireByLibelle(String email) {
        Optional<Itineraire> itineraire= itineraireRepository.findItineraireByLibelle(email);
        if (!itineraire.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromItineraire(itineraire.get());
    }

    @Override
    public ItineraireDto saveItineraire(ItineraireDto itineraireDto) {
      Itineraire itineraire = mapper.fromItineraireDto(itineraireDto);
      if( itineraire == null){
          throw new IllegalArgumentException("Itineraire was Null");
      }
      itineraireValidators.validate(itineraire);
      boolean exists = itineraireRepository.existsByLibelle(itineraireDto.getLibelle());
     if(exists) {
         throw  new DuplicateEntryException("Itineraire was Found with this Libelle");
     }
     Itineraire saveditineraire = itineraireRepository.save(itineraire);
     return mapper.fromItineraire(itineraire);
    }

    @Override
    public ItineraireDto updateItineraire(ItineraireDto itinerairedto, Long id) {
      Optional<Itineraire> itineraire1 = itineraireRepository.findById(id);
      if(itineraire1.isPresent()){
      Itineraire itineraire2 = itineraire1.get();
      itineraire2.setId(id);
      itineraireValidators.validate(itineraire2);
      if(itineraireRepository.existsByLibelle(itineraire2.getLibelle())){
          throw new DuplicateEntryException("A Itineraire was Found with this Libelle" );
      }
      Itineraire itineraire3 =itineraireRepository.saveAndFlush(itineraire2);
      return mapper.fromItineraire(itineraire3);
      }else {
          throw  new EntityNotFoundException("Itineraire NotFound");
      }
    }

    @Override
    public void deleteItineraire(Long id) {
        if(itineraireRepository.findById(id).isPresent()){
            itineraireRepository.deleteById(id);
        } else {
            throw  new EntityNotFoundException("Iteneraire with thi sid Not Found");
        }

    }


    public PageResponse<ItineraireDto> getItineraires(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Itineraire> Itineraires = itineraireRepository.findAll(pageRequest);
        List<ItineraireDto> ItineraireList = Itineraires.map(mapper::fromItineraire).getContent();

        return new PageResponse<>(
                ItineraireList,
                Itineraires.getNumber(),
                Itineraires.getSize(),
                Itineraires.getTotalElements(),
                Itineraires.getTotalPages(),
                Itineraires.isFirst(),
                Itineraires.isLast()
        );
    }
}
