package com.hadjsalem.agencevoyage.services;

import com.hadjsalem.agencevoyage.dtos.ClientDto;

public interface ClientService {
    ClientDto findClientById(Long id);
    ClientDto findClientByFirstName(String firstName);
    ClientDto saveClient(ClientDto client);
    ClientDto updateClient(ClientDto client,Long id);
    void deleteClient(Long id);




}
