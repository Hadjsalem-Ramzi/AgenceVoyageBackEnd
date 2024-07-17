package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;

public interface GuidePapierService {
    GuidePapierDto findGuidePapierById(Long id);
    GuidePapierDto findGuidePapierByLibelle(String libelle);
    GuidePapierDto saveGuidePapier(GuidePapierDto guidePapier);
    GuidePapierDto updateGuidePapier(GuidePapierDto guidePapier,Long id);
    void deleteGuidePapier(Long id);




}
