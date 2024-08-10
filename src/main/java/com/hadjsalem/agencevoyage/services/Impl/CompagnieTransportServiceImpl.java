package com.hadjsalem.agencevoyage.services.Impl;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import com.hadjsalem.agencevoyage.entities.Client;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;
import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.mapper.CompagnieTransportMapper;
import com.hadjsalem.agencevoyage.repositories.CompagnieTransportRepository;
import com.hadjsalem.agencevoyage.services.CompagnieTransportService;
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
public class CompagnieTransportServiceImpl implements CompagnieTransportService {
    private CompagnieTransportRepository compagnieTransportRepository;
    private CompagnieTransportMapper mapper;
    private ObjectsValidators<CompagnieTransport> compagnieTransportValidators;


    @Override
    public CompagnieTransportDto findCompagnieTransportById(Long id) {
        Optional<CompagnieTransport> optionalCompagnieTransport = compagnieTransportRepository.findById(id);
        return optionalCompagnieTransport.map(mapper::fromCompagnieTransport).orElseThrow(() -> new EntityNotFoundException("CompagnieTransport Not Found"));

    }

    @Override
    public CompagnieTransportDto findCompagnieTransportByNom(String nom) {
        Optional<CompagnieTransport> compagnieTransport = compagnieTransportRepository.findCompagnieTransportByNom(nom);
        if (!compagnieTransport.isPresent()) {
            throw new EntityNotFoundException("Client Not Found");
        }
        return mapper.fromCompagnieTransport(compagnieTransport.get());
    }

    @Override
    public CompagnieTransportDto saveCompagnieTransport(CompagnieTransportDto compagnieTransportDto) {
        CompagnieTransport compagnieTransport = mapper.fromCompagnieTransportDto(compagnieTransportDto);
        if (compagnieTransport == null) {
            throw new IllegalArgumentException("compagnieTransport est null");
        }
        //Validation des champs
        compagnieTransportValidators.validate(compagnieTransport);
        boolean exists = compagnieTransportRepository.existsByNumTel(compagnieTransportDto.getNumTel());
        if (exists) {
            throw new DuplicateEntryException("un compagnie Transport exist avec cette numéro de Télephone");
        }
        CompagnieTransport savedCompagnieTransport = compagnieTransportRepository.save(compagnieTransport);
        return mapper.fromCompagnieTransport(savedCompagnieTransport);
    }

    @Override
    public CompagnieTransportDto updateCompagnieTransport(CompagnieTransportDto compagnieTransportdto, Long id) {
        Optional<CompagnieTransport> compagnieTransport1 = compagnieTransportRepository.findById(id);
        if (compagnieTransport1.isPresent()) {
            CompagnieTransport compagnieTransport2 = compagnieTransport1.get();
            compagnieTransport2.setId(id);
            compagnieTransportValidators.validate(compagnieTransport2);
            if (compagnieTransportRepository.existsByNumTel(compagnieTransport2.getNumTel())) {
                throw new DuplicateEntryException("Un Compagnie Transport est existe avec cette Numéro de Télephone ");
            }
            CompagnieTransport compagnieTransport3 = compagnieTransportRepository.saveAndFlush(compagnieTransport2);
            return mapper.fromCompagnieTransport(compagnieTransport3);
            } else {
            throw  new EntityNotFoundException("CompagnieTransport Not Found");
        }
        }

        @Override
        public void deleteCompagnieTransport (Long id){

          if(compagnieTransportRepository.findById(id).isPresent()){
              compagnieTransportRepository.deleteById(id);
          } else {
              throw new EntityNotFoundException("Compagnie Transport with this Id Not Found");
          }

        }

        public PageResponse<CompagnieTransportDto> getCompagnieTransports ( int page, int size){
            Pageable pageRequest = PageRequest.of(page, size);
            Page<CompagnieTransport> CompagnieTransports = compagnieTransportRepository.findAll(pageRequest);
            List<CompagnieTransportDto> CompagnieTransportList = CompagnieTransports.map(mapper::fromCompagnieTransport).getContent();

            return new PageResponse<>(
                    CompagnieTransportList,
                    CompagnieTransports.getNumber(),
                    CompagnieTransports.getSize(),
                    CompagnieTransports.getTotalElements(),
                    CompagnieTransports.getTotalPages(),
                    CompagnieTransports.isFirst(),
                    CompagnieTransports.isLast()
            );
        }

    }
