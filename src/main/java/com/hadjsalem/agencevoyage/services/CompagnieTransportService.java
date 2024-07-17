package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;

public interface CompagnieTransportService {
    CompagnieTransportDto findCompagnieTransportById(Long id);
    CompagnieTransportDto findCompagnieTransportByNom(String nom);
    CompagnieTransportDto saveCompagnieTransport(CompagnieTransportDto compagnieTransport);
    CompagnieTransportDto updateCompagnieTransport(CompagnieTransportDto compagnieTransport,Long id);
    void deleteCompagnieTransport(Long id);




}
