package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.entities.CompagnieTransport;

public interface CompagnieTransportService {
    CompagnieTransportDto findCompagnieTransportById(Long id);
    CompagnieTransportDto findCompagnieTransportByNom(String nom);
    CompagnieTransportDto saveCompagnieTransport(CompagnieTransportDto compagnieTransport);
    CompagnieTransportDto updateCompagnieTransport(CompagnieTransportDto compagnieTransport,Long id);
    void deleteCompagnieTransport(Long id);

    PageResponse<CompagnieTransportDto> getCompagnieTransports(int page,int size);


}
