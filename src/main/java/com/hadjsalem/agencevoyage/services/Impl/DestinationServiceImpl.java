package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import com.hadjsalem.agencevoyage.entities.Destination;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.DestinationMapper;
import com.hadjsalem.agencevoyage.repositories.DestinationRepository;
import com.hadjsalem.agencevoyage.services.DestinationService;
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
public class DestinationServiceImpl implements DestinationService {
    private DestinationRepository destinationRepository;
    private DestinationMapper mapper;
    private ObjectsValidators<Destination> destinationValidators;


    @Override
    public DestinationDto findDestinationById(Long id) {
    Optional<Destination> optionalDestination =destinationRepository.findById(id);
    return optionalDestination.map(mapper::fromdestination).orElseThrow(()->new EntityNotFoundException("Destination Not Found"));

    }

    @Override
    public DestinationDto findDestinationByVille(String ville) {
        Optional<Destination> destination = destinationRepository.findDestinationByVille(ville);
        if (!destination.isPresent()) {
            throw new EntityNotFoundException("destination Not Found");
        }
        return mapper.fromdestination(destination.get());
    }

    @Override
    public DestinationDto saveDestination(DestinationDto destinationDto) {
      Destination destination = mapper.fromdestinationDto(destinationDto);
      if(destination == null){
          throw new IllegalArgumentException("destiantion est null");
      }
      // Validation des champs
        destinationValidators.validate(destination);
      boolean exists = destinationRepository.existsByVille(destinationDto.getVille());
      if(exists){
          throw  new DuplicateEntryException("un destination est existe avec ce nom");
      }
      Destination savedDestination = destinationRepository.save(destination);
      return mapper.fromdestination(savedDestination);
    }

    @Override
    public DestinationDto updateDestination(DestinationDto Destinationdto, Long id) {
        Optional<Destination> Destination1 = destinationRepository.findById(id);
        if (Destination1.isPresent()) {
            Destination destination2 = Destination1.get();
            destination2.setId(id);
            destinationValidators.validate(destination2);
            if (destinationRepository.existsByPays(destination2.getPays())) {
                throw new DuplicateEntryException("un destination est existe avec cette Pays");
            }
            Destination destination3 = destinationRepository.saveAndFlush(destination2);
            return mapper.fromdestination(destination3);
        } else {
            throw new EntityNotFoundException("Destination NotFound");
        }
    }
    @Override
    public void deleteDestination(Long id) {
        if(destinationRepository.findById(id).isPresent()){
            destinationRepository.deleteById(id);
        }
         else {
             throw new EntityNotFoundException("Destination with this id Not Found");
        }
    }

    @Override
    public PageResponse<DestinationDto> getDestination(int page, int size) {
        Pageable pageRequest = PageRequest.of(page,size);
        Page<Destination> Destinations = destinationRepository.findAll(pageRequest);
        List<DestinationDto> DestinationList = Destinations.map(mapper::fromdestination).getContent();

        return new PageResponse<>(
                DestinationList,
                Destinations.getNumber(),
                Destinations.getSize(),
                Destinations.getTotalElements(),
                Destinations.getTotalPages(),
                Destinations.isFirst(),
                Destinations.isLast()
        );
    }


}
