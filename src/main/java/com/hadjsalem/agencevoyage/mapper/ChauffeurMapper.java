package com.hadjsalem.agencevoyage.mapper;

import com.hadjsalem.agencevoyage.dtos.ChauffeurDto;
import com.hadjsalem.agencevoyage.entities.Chauffeur;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ChauffeurMapper {

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChauffeurMapper.class);

    public Chauffeur fromChauffeurDto(ChauffeurDto chauffeurDto) {
        logger.info("Mapping ChauffeurDto to Chauffeur: {}", chauffeurDto);
        Chauffeur chauffeur = modelMapper.map(chauffeurDto, Chauffeur.class);
        logger.info("Mapped Chauffeur: {}", chauffeur);
        return chauffeur;
    }

    public ChauffeurDto fromChaufeur(Chauffeur chauffeur) {
        logger.info("Mapping Chauffeur to ChauffeurDto: {}", chauffeur);
        ChauffeurDto chauffeurDto = modelMapper.map(chauffeur, ChauffeurDto.class);
        logger.info("Mapped ChauffeurDto: {}", chauffeurDto);
        return chauffeurDto;
    }
}
