package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.dtos.DestinationDto;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import com.hadjsalem.agencevoyage.entities.Destination;
import com.hadjsalem.agencevoyage.mapper.DestinationMapper;
import com.hadjsalem.agencevoyage.repositories.DestinationRepository;
import com.hadjsalem.agencevoyage.services.DestinationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private DestinationRepository destinationRepository;
    private DestinationMapper mapper;


    @Override
    public DestinationDto findDestinationById(Long id) {
    Optional<Destination> optionalDestination =destinationRepository.findById(id);
    return optionalDestination.map(mapper::fromdestination).orElseThrow(()->new NoSuchElementException("Destination Not Found"));

    }

    @Override
    public DestinationDto findDestinationByVille(String ville) {
        Optional<Destination> destination = destinationRepository.findDestinationByVille(ville);
        if (!destination.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromdestination(destination.get());
    }

    @Override
    public DestinationDto saveDestination(DestinationDto destinationDto) {
      Destination Destination1 = mapper.fromdestinationDto(destinationDto);
      Destination Destination2= destinationRepository.save(Destination1);
      return mapper.fromdestination(Destination2);
    }

    @Override
    public DestinationDto updateDestination(DestinationDto Destinationdto, Long id) {
      Optional<Destination> Destination1 = destinationRepository.findById(id);
      if(Destination1.isPresent()){
      Destination Destination2 = Destination1.get();
      Destination Destination3= destinationRepository.saveAndFlush(Destination2);
      return mapper.fromdestination(Destination3);
      }else {
          throw  new NoSuchElementException("Destination NotFound");
      }
    }

    @Override
    public void deleteDestination(Long id) {
       destinationRepository.deleteById(id);
    }
}
