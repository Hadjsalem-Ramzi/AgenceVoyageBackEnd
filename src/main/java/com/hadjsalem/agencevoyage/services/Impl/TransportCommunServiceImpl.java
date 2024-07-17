package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import com.hadjsalem.agencevoyage.entities.TransportCommun;
import com.hadjsalem.agencevoyage.mapper.TransportCommunMapper;
import com.hadjsalem.agencevoyage.repositories.TransportCommunRepository;
import com.hadjsalem.agencevoyage.services.TransportCommunService;
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
public class TransportCommunServiceImpl implements TransportCommunService {
    private TransportCommunRepository transportCommunRepository;
    private TransportCommunMapper mapper;


    @Override
    public TransportCommunDto findTransportCommunById(Long id) {
    Optional<TransportCommun> optionalTransportCommun =transportCommunRepository.findById(id);
    return optionalTransportCommun.map(mapper::fromTransportCommun).orElseThrow(()->new NoSuchElementException("TransportCommun Not Found"));

    }

    public TransportCommunDto findTransportCommunByNom(String firstName) {
        Optional<TransportCommun> TransportCommun = transportCommunRepository.findTransportCommunByNom(firstName);
        if (!TransportCommun.isPresent()) {
            throw new RuntimeException("TransportCommun Not Found");
        }
        return mapper.fromTransportCommun(TransportCommun.get());
    }

    @Override
    public TransportCommunDto saveTransportCommun(TransportCommunDto TransportCommunDto) {
      TransportCommun TransportCommun1 = mapper.fromTransportCommunDto(TransportCommunDto);
      TransportCommun TransportCommun2= transportCommunRepository.save(TransportCommun1);
      return mapper.fromTransportCommun(TransportCommun2);
    }

    @Override
    public TransportCommunDto updateTransportCommun(TransportCommunDto TransportCommundto, Long id) {
      Optional<TransportCommun> TransportCommun1 = transportCommunRepository.findById(id);
      if(TransportCommun1.isPresent()){
      TransportCommun TransportCommun2 = TransportCommun1.get();
      TransportCommun TransportCommun3= transportCommunRepository.saveAndFlush(TransportCommun2);
      return mapper.fromTransportCommun(TransportCommun3);
      }else {
          throw  new NoSuchElementException("TransportCommun NotFound");
      }
    }

    @Override
    public void deleteTransportCommun(Long id) {
       transportCommunRepository.deleteById(id);
    }

    public PageResponse<TransportCommunDto> getTransportCommuns(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<TransportCommun> TransportCommuns = transportCommunRepository.findAll(pageRequest);
        List<TransportCommunDto> TransportCommunList = TransportCommuns.map(mapper::fromTransportCommun).getContent();

        return new PageResponse<>(
                TransportCommunList,
                TransportCommuns.getNumber(),
                TransportCommuns.getSize(),
                TransportCommuns.getTotalElements(),
                TransportCommuns.getTotalPages(),
                TransportCommuns.isFirst(),
                TransportCommuns.isLast()
        );
    }
}
