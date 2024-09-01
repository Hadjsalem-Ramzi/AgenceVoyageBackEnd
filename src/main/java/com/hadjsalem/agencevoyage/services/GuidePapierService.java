package com.hadjsalem.agencevoyage.services;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.GuidePapierDto;
import com.hadjsalem.agencevoyage.entities.GuidePapier;

public interface GuidePapierService {
    GuidePapierDto findGuidePapierById(Long id);
    GuidePapierDto findGuidePapierByName(String libelle);
    GuidePapierDto saveGuidePapier(GuidePapierDto guidePapier);
    GuidePapierDto updateGuidePapier(GuidePapierDto guidePapier,Long id);
    void deleteGuidePapier(Long id);
    PageResponse<GuidePapierDto> getGuidePapiers(int page,int size);



}
