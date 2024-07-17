package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;

public interface TransportCommunService {
    TransportCommunDto findTransportCommunById(Long id);
    TransportCommunDto findTransportCommunByNom(String nom);
    TransportCommunDto saveTransportCommun(TransportCommunDto transportCommun);
    TransportCommunDto updateTransportCommun(TransportCommunDto transportCommun,Long id);
    void deleteTransportCommun(Long id);




}
