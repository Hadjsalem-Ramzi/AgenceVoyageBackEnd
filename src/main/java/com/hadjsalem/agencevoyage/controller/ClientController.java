package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.ClientApi;
import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.services.Impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientApi {

    private  final ClientServiceImpl clientService;


    @Override
    public ClientDto saveclient(ClientDto client) {
        return clientService.saveClient(client);
    }

    @Override
    public ClientDto findclientById(Long id) {
        return clientService.findClientById(id);
    }

    @Override
    public ClientDto findClientByEmail(String email) {
        return clientService.findClientByEmail(email);
    }

    @Override
    public ClientDto updateclient(ClientDto client, Long id) {
        return clientService.updateClient(client, id);
    }

    @Override
    public void deleteclient(Long id) {
      clientService.deleteClient(id);
    }

    @Override
    public PageResponse<ClientDto> getclients(int page, int size) {
        return clientService.getClients(page, size);
    }
}
