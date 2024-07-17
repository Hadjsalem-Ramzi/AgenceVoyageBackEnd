package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import com.hadjsalem.agencevoyage.mapper.MoyenTransportMapper;
import com.hadjsalem.agencevoyage.repositories.MoyenTransportRepository;
import com.hadjsalem.agencevoyage.services.MoyenTransportService;
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

    public PageResponse<MoyenTransportDto> getMoyenTransports(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<MoyenTransport> MoyenTransports = moyenTransportRepository.findAll(pageRequest);
        List<MoyenTransportDto> MoyenTransportList = MoyenTransports.map(mapper::fromMoyenTransport).getContent();

        return new PageResponse<>(
                MoyenTransportList,
                MoyenTransports.getNumber(),
                MoyenTransports.getSize(),
                MoyenTransports.getTotalElements(),
                MoyenTransports.getTotalPages(),
                MoyenTransports.isFirst(),
                MoyenTransports.isLast()
        );
    }
    
}
