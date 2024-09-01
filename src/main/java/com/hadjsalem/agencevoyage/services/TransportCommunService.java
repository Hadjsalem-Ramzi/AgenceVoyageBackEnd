package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;

public interface TransportCommunService {
    TransportCommunDto findTransportCommunById(Long id);
    TransportCommunDto findTransportCommunByName(String nom);
    TransportCommunDto saveTransportCommun(TransportCommunDto transportCommun);
    TransportCommunDto updateTransportCommun(TransportCommunDto transportCommun,Long id);
    void deleteTransportCommun(Long id);

   PageResponse<TransportCommunDto> getTransportCommuns(int page, int size);


}
