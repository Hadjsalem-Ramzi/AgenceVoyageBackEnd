package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.MoyenTransportApi;
import com.hadjsalem.agencevoyage.dtos.MoyenTransportDto;
import com.hadjsalem.agencevoyage.services.Impl.MoyenTransportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MoyenTransportController implements MoyenTransportApi {

    private  final MoyenTransportServiceImpl moyenTransportService ;


    @Override
    public MoyenTransportDto saveMoyenTransport(MoyenTransportDto MoyenTransport) {
        return moyenTransportService.saveMoyenTransport(MoyenTransport) ;
    }

    @Override
    public MoyenTransportDto findMoyenTransportById(Long id) {
        return moyenTransportService.findMoyenTransportById(id) ;
    }

    @Override
    public MoyenTransportDto findMoyenTransportByName(String name) {
        return moyenTransportService.findMoyenTransportByName(name) ;
    }

    @Override
    public MoyenTransportDto updateMoyenTransport(MoyenTransportDto MoyenTransport, Long id) {
        return moyenTransportService.updateMoyenTransport(MoyenTransport, id) ;
    }

    @Override
    public void deleteMoyenTransport(Long id) {
     moyenTransportService.deleteMoyenTransport(id) ;
    }

    @Override
    public PageResponse<MoyenTransportDto> getMoyenTransports(int page, int size) {
        return moyenTransportService.getMoyenTransports(page, size) ;
    }
}
