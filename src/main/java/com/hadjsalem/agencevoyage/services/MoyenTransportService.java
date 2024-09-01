package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;

public interface MoyenTransportService {
    MoyenTransportDto findMoyenTransportById(Long id);
    MoyenTransportDto findMoyenTransportByName(String name);
    MoyenTransportDto saveMoyenTransport( MoyenTransportDto moyenTransport);
    MoyenTransportDto updateMoyenTransport( MoyenTransportDto moyenTransport,Long id);
   void deleteMoyenTransport(Long id);

   PageResponse<MoyenTransportDto> getMoyenTransports(int page,int size);


}
