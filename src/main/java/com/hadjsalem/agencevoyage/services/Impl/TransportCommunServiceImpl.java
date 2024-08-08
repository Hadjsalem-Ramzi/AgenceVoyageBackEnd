package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import com.hadjsalem.agencevoyage.entities.TransportCommun;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.TransportCommunMapper;
import com.hadjsalem.agencevoyage.repositories.TransportCommunRepository;
import com.hadjsalem.agencevoyage.services.TransportCommunService;
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
public class TransportCommunServiceImpl implements TransportCommunService {
    private TransportCommunRepository transportCommunRepository;
    private TransportCommunMapper mapper;
    private ObjectsValidators<TransportCommun> transportCommunValidators;


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
    public TransportCommunDto saveTransportCommun(TransportCommunDto transportCommunDto) {
      TransportCommun transportCommun1 = mapper.fromTransportCommunDto(transportCommunDto);
     if( transportCommun1 == null){
         throw  new IllegalArgumentException("transportCommun1 est null");
     }
     transportCommunValidators.validate(transportCommun1);
     boolean exists = transportCommunRepository.existsByNumTel(transportCommun1.getNumTel());
     if(exists){
         throw  new DuplicateEntryException("transport Commun  avec ce numéro existe");
     }
     return  mapper.fromTransportCommun(transportCommun1);
    }

    @Override
    public TransportCommunDto updateTransportCommun(TransportCommunDto transportCommundto, Long id) {
      Optional<TransportCommun> transportCommun1 = transportCommunRepository.findById(id);
      if(transportCommun1.isPresent()){
      TransportCommun transportCommun2 = transportCommun1.get();
        transportCommun2.setId(id);
        transportCommunValidators.validate(transportCommun2);
        if(transportCommunRepository.existsByNumTel(transportCommun2.getNumTel())){
            throw new DuplicateEntryException("un transport Commun with this Numéro Tél existe");
        }
        TransportCommun transportCommun3 = transportCommunRepository.saveAndFlush(transportCommun2);
        return mapper.fromTransportCommun(transportCommun3);
      }else {
          throw  new NoSuchElementException("TransportCommun NotFound");
      }
    }

    @Override
    public void deleteTransportCommun(Long id) {
        if( transportCommunRepository.findById(id).isPresent()){
            transportCommunRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("transport with this id not Found");
        }

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
