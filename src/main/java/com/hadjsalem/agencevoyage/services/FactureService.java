package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.dtos.FactureDto;

public interface FactureService {
    FactureDto findFactureById(Long id);
    FactureDto findFactureByDesignation(String designation);
    FactureDto saveFacture(FactureDto facture);
    FactureDto updateFacture(FactureDto facture,Long id);
    void deleteFacture(Long id);




}
