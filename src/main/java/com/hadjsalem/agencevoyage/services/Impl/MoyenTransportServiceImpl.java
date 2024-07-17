package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import com.hadjsalem.agencevoyage.mapper.MoyenTransportMapper;
import com.hadjsalem.agencevoyage.repositories.MoyenTransportRepository;
import com.hadjsalem.agencevoyage.services.MoyenTransportService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MoyenTransportServiceImpl implements MoyenTransportService {
    private MoyenTransportRepository moyenTransportRepository;
    private MoyenTransportMapper mapper;


    @Override
    public MoyenTransportDto findMoyenTransportById(Long id) {
    Optional<MoyenTransport> optionalMoyenTransport =moyenTransportRepository.findById(id);
    return optionalMoyenTransport.map(mapper::fromMoyenTransport).orElseThrow(()->new NoSuchElementException("MoyenTransport Not Found"));

    }

    @Override
    public MoyenTransportDto findMoyenTransportByNom(String email) {
        Optional<MoyenTransport> moyenTransport= moyenTransportRepository.findMoyenTransportByNom(email);
        if (!moyenTransport.isPresent()) {
            throw new RuntimeException("Client Not Found");
        }
        return mapper.fromMoyenTransport(moyenTransport.get());
    }

    @Override
    public MoyenTransportDto saveMoyenTransport(MoyenTransportDto MoyenTransportDto) {
      MoyenTransport MoyenTransport1 = mapper.fromMoyenTransportDto(MoyenTransportDto);
      MoyenTransport MoyenTransport2= moyenTransportRepository.save(MoyenTransport1);
      return mapper.fromMoyenTransport(MoyenTransport2);
    }

    @Override
    public MoyenTransportDto updateMoyenTransport(MoyenTransportDto MoyenTransportdto, Long id) {
      Optional<MoyenTransport> MoyenTransport1 = moyenTransportRepository.findById(id);
      if(MoyenTransport1.isPresent()){
      MoyenTransport MoyenTransport2 = MoyenTransport1.get();
      MoyenTransport MoyenTransport3= moyenTransportRepository.saveAndFlush(MoyenTransport2);
      return mapper.fromMoyenTransport(MoyenTransport3);
      }else {
          throw  new NoSuchElementException("MoyenTransport NotFound");
      }
    }

    @Override
    public void deleteMoyenTransport(Long id) {
       moyenTransportRepository.deleteById(id);
    }
}
