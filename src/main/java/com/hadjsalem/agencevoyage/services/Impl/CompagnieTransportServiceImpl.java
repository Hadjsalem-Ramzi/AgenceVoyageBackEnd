package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.entities.Client;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import com.hadjsalem.agencevoyage.mapper.CompagnieTransportMapper;
import com.hadjsalem.agencevoyage.repositories.CompagnieTransportRepository;
import com.hadjsalem.agencevoyage.services.CompagnieTransportService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CompagnieTransportServiceImpl implements CompagnieTransportService {
    private CompagnieTransportRepository compagnieTransportRepository;
    private CompagnieTransportMapper mapper;


    @Override
    public CompagnieTransportDto findCompagnieTransportById(Long id) {
    Optional<CompagnieTransport> optionalCompagnieTransport =compagnieTransportRepository.findById(id);
    return optionalCompagnieTransport.map(mapper::fromCompagnieTransport).orElseThrow(()->new NoSuchElementException("CompagnieTransport Not Found"));

    }

    @Override
    public CompagnieTransportDto findCompagnieTransportByNom(String nom) {
        Optional<CompagnieTransport> compagnieTransport = compagnieTransportRepository.findCompagnieTransportByNom(nom);
        if (!compagnieTransport.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromCompagnieTransport(compagnieTransport.get());
    }

    @Override
    public CompagnieTransportDto saveCompagnieTransport(CompagnieTransportDto CompagnieTransportDto) {
      CompagnieTransport CompagnieTransport1 = mapper.fromCompagnieTransportDto(CompagnieTransportDto);
      CompagnieTransport CompagnieTransport2= compagnieTransportRepository.save(CompagnieTransport1);
      return mapper.fromCompagnieTransport(CompagnieTransport2);
    }

    @Override
    public CompagnieTransportDto updateCompagnieTransport(CompagnieTransportDto CompagnieTransportdto, Long id) {
      Optional<CompagnieTransport> CompagnieTransport1 = compagnieTransportRepository.findById(id);
      if(CompagnieTransport1.isPresent()){
      CompagnieTransport CompagnieTransport2 = CompagnieTransport1.get();
      CompagnieTransport CompagnieTransport3= compagnieTransportRepository.saveAndFlush(CompagnieTransport2);
      return mapper.fromCompagnieTransport(CompagnieTransport3);
      }else {
          throw  new NoSuchElementException("CompagnieTransport NotFound");
      }
    }

    @Override
    public void deleteCompagnieTransport(Long id) {
       compagnieTransportRepository.deleteById(id);
    }
}
