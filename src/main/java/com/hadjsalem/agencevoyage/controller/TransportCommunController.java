package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.Common.PageResponse;
import com.hadjsalem.agencevoyage.controller.api.TransportCommunApi;
import com.hadjsalem.agencevoyage.dtos.TransportCommunDto;
import com.hadjsalem.agencevoyage.services.Impl.TransportCommunServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransportCommunController implements TransportCommunApi {

    private  final  TransportCommunServiceImpl transportCommunService ;


    @Override
    public TransportCommunDto saveTransportCommun(TransportCommunDto TransportCommun) {
        return transportCommunService.saveTransportCommun(TransportCommun);
    }

    @Override
    public TransportCommunDto findTransportCommunById(Long id) {
        return transportCommunService.findTransportCommunById(id);
    }

    @Override
    public TransportCommunDto findTransportCommunByName(String name) {
        return transportCommunService.findTransportCommunByName(name);
    }

    @Override
    public TransportCommunDto updateTransportCommun(TransportCommunDto TransportCommun, Long id) {
        return transportCommunService.updateTransportCommun(TransportCommun, id);
    }

    @Override
    public void deleteTransportCommun(Long id) {
      transportCommunService.deleteTransportCommun(id);
    }

    @Override
    public PageResponse<TransportCommunDto> getTransportCommuns(int page, int size) {
        return transportCommunService.getTransportCommuns(page, size);
    }
}
