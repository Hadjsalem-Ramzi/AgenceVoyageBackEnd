package com.hadjsalem.agencevoyage.services.Impl;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import com.hadjsalem.agencevoyage.entities.MoyenTransport;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.MoyenTransportMapper;
import com.hadjsalem.agencevoyage.repositories.MoyenTransportRepository;
import com.hadjsalem.agencevoyage.services.MoyenTransportService;
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
public class MoyenTransportServiceImpl implements MoyenTransportService {
    private MoyenTransportRepository moyenTransportRepository;
    private MoyenTransportMapper mapper;
    private ObjectsValidators<MoyenTransport> moyenTransportValidators;

    @Override
    public MoyenTransportDto findMoyenTransportById(Long id) {
        Optional<MoyenTransport> optionalMoyenTransport = moyenTransportRepository.findById(id);
        return optionalMoyenTransport.map(mapper::fromMoyenTransport).orElseThrow(() -> new EntityNotFoundException("MoyenTransport Not Found"));

    }

    @Override
    public MoyenTransportDto findMoyenTransportByName(String name) {
        Optional<MoyenTransport> moyenTransport = moyenTransportRepository.findMoyenTransportByName(name);
        if (!moyenTransport.isPresent()) {
            throw new EntityNotFoundException("MoyenTransport Not Found");
        }
        return mapper.fromMoyenTransport(moyenTransport.get());
    }

    @Override
    public MoyenTransportDto saveMoyenTransport(MoyenTransportDto moyenTransportDto) {
        MoyenTransport moyenTransport = mapper.fromMoyenTransportDto(moyenTransportDto);
        if (moyenTransport == null) {
            throw new IllegalArgumentException("Moyen Transport was null");
        }
        moyenTransportValidators.validate(moyenTransport);
        boolean exists = moyenTransportRepository.existsByName(moyenTransportDto.getName());
        if (exists) {
            throw new DuplicateEntryException("moyen Transport with this Nom was Found");
        }
        MoyenTransport savedMoyenTransport = moyenTransportRepository.save(moyenTransport);
        return mapper.fromMoyenTransport(savedMoyenTransport);
    }

    @Override
    public MoyenTransportDto updateMoyenTransport(MoyenTransportDto moyenTransportdto, Long id) {
        Optional<MoyenTransport> moyenTransport1 = moyenTransportRepository.findById(id);
        if (moyenTransport1.isPresent()) {
            MoyenTransport moyenTransport2 = moyenTransport1.get();
            moyenTransport2.setId(id);
            moyenTransportValidators.validate(moyenTransport2);
            if (moyenTransportRepository.existsByName(moyenTransport2.getName())) {
                throw new DuplicateEntryException("A moyen Transport was Found with this Nom");
            }
            MoyenTransport moyenTransport3= moyenTransportRepository.saveAndFlush(moyenTransport2);
            return mapper.fromMoyenTransport(moyenTransport3);
        } else {
            throw new EntityNotFoundException("MoyenTransport NotFound");
        }
    }
    @Override
    public void deleteMoyenTransport(Long id) {
        if(moyenTransportRepository.findById(id).isPresent()){
            moyenTransportRepository.deleteById(id);
        } else {
            throw  new EntityNotFoundException("moyen Transport with this Nom not Found");
        }

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
