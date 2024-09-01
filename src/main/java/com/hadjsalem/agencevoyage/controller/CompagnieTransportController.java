package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.ClientApi;
import com.hadjsalem.agencevoyage.controller.api.CompagnieTransportApi;
import com.hadjsalem.agencevoyage.dtos.ClientDto;
import com.hadjsalem.agencevoyage.dtos.CompagnieTransportDto;
import com.hadjsalem.agencevoyage.services.CompagnieTransportService;
import com.hadjsalem.agencevoyage.services.Impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompagnieTransportController implements CompagnieTransportApi {

    private final CompagnieTransportService compagnieTransportService;


    @Override
    public CompagnieTransportDto saveCompagnieTransport(CompagnieTransportDto CompagnieTransport) {
        return compagnieTransportService.saveCompagnieTransport(CompagnieTransport);
    }

    @Override
    public CompagnieTransportDto findCompagnieTransportById(Long id) {
        return compagnieTransportService.findCompagnieTransportById(id);
    }

    @Override
    public CompagnieTransportDto findCompagnieTransportByName(String name) {
        return  compagnieTransportService.findCompagnieTransportByName(name);
    }


    @Override
    public CompagnieTransportDto updateCompagnieTransport(CompagnieTransportDto CompagnieTransport, Long id) {
        return compagnieTransportService.updateCompagnieTransport(CompagnieTransport, id);
    }

    @Override
    public void deleteCompagnieTransport(Long id) {
     compagnieTransportService.deleteCompagnieTransport(id);
    }

    @Override
    public PageResponse<CompagnieTransportDto> getCompagnieTransports(int page, int size) {
        return compagnieTransportService.getCompagnieTransports(page, size);
    }
}