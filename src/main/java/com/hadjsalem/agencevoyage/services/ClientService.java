package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.dtos.ClientDto;

public interface ClientService {
    ClientDto findClientById(Long id);
    ClientDto findClientByEmail(String email);
    ClientDto saveClient(ClientDto client);
    ClientDto updateClient(ClientDto client,Long id);
    void deleteClient(Long id);
    PageResponse<ClientDto> getClients(int page,int size);




}
