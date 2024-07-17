package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.Facture;
import com.hadjsalem.agencevoyage.entities.GuidePapier;
import com.hadjsalem.agencevoyage.mapper.GuidePapierMapper;
import com.hadjsalem.agencevoyage.repositories.GuidePapierRepository;
import com.hadjsalem.agencevoyage.services.GuidePapierService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GuidePapierServiceImpl implements GuidePapierService {
    private GuidePapierRepository guidePapierRepository;
    private GuidePapierMapper mapper;


    @Override
    public GuidePapierDto findGuidePapierById(Long id) {
    Optional<GuidePapier> optionalGuidePapier =guidePapierRepository.findById(id);
    return optionalGuidePapier.map(mapper::fromGuidePapier).orElseThrow(()->new NoSuchElementException("GuidePapier Not Found"));

    }

    @Override
    public GuidePapierDto findGuidePapierByLibelle(String libelle) {

        Optional<GuidePapier> guidePapier = guidePapierRepository.findGuidePapierByLibelle(libelle);
        if (!guidePapier.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromGuidePapier(guidePapier.get());
    }

    @Override
    public GuidePapierDto saveGuidePapier(GuidePapierDto GuidePapierDto) {
      GuidePapier GuidePapier1 = mapper.fromGuidePapierDto(GuidePapierDto);
      GuidePapier GuidePapier2= guidePapierRepository.save(GuidePapier1);
      return mapper.fromGuidePapier(GuidePapier2);
    }

    @Override
    public GuidePapierDto updateGuidePapier(GuidePapierDto GuidePapierdto, Long id) {
      Optional<GuidePapier> GuidePapier1 = guidePapierRepository.findById(id);
      if(GuidePapier1.isPresent()){
      GuidePapier GuidePapier2 = GuidePapier1.get();
      GuidePapier GuidePapier3= guidePapierRepository.saveAndFlush(GuidePapier2);
      return mapper.fromGuidePapier(GuidePapier3);
      }else {
          throw  new NoSuchElementException("GuidePapier NotFound");
      }
    }

    @Override
    public void deleteGuidePapier(Long id) {
       guidePapierRepository.deleteById(id);
    }
}
