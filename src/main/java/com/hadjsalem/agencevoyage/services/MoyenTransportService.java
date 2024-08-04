package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;

public interface MoyenTransportService {
    MoyenTransportDto findMoyenTransportById(Long id);
    MoyenTransportDto findMoyenTransportByNom(String nom);
    MoyenTransportDto saveMoyenTransport( MoyenTransportDto moyenTransport);
    MoyenTransportDto updateMoyenTransport( MoyenTransportDto moyenTransport,Long id);
   void deleteMoyenTransport(Long id);

   PageResponse<MoyenTransportDto> getMoyenTransports(int page,int size);


}
