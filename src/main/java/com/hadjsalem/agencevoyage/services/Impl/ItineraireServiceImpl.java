package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ItineraireDto;
import com.hadjsalem.agencevoyage.entities.Hotel;
import com.hadjsalem.agencevoyage.entities.Itineraire;
import com.hadjsalem.agencevoyage.mapper.ItineraireMapper;
import com.hadjsalem.agencevoyage.repositories.ItineraireRepository;
import com.hadjsalem.agencevoyage.services.ItineraireService;
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
    public ItineraireDto saveItineraire(ItineraireDto ItineraireDto) {
      Itineraire Itineraire1 = mapper.fromItineraireDto(ItineraireDto);
      Itineraire Itineraire2= itineraireRepository.save(Itineraire1);
      return mapper.fromItineraire(Itineraire2);
    }

    @Override
    public ItineraireDto updateItineraire(ItineraireDto Itinerairedto, Long id) {
      Optional<Itineraire> Itineraire1 = itineraireRepository.findById(id);
      if(Itineraire1.isPresent()){
      Itineraire Itineraire2 = Itineraire1.get();
      Itineraire Itineraire3= itineraireRepository.saveAndFlush(Itineraire2);
      return mapper.fromItineraire(Itineraire3);
      }else {
          throw  new NoSuchElementException("Itineraire NotFound");
      }
    }

    @Override
    public void deleteItineraire(Long id) {
       itineraireRepository.deleteById(id);
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
