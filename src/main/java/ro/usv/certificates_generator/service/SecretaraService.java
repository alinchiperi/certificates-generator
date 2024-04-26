package ro.usv.certificates_generator.service;

import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.model.CerereStatus;

import java.util.List;

@Service
public class SecretaraService {

    private final AdeverinteService adeverinteService;

    public SecretaraService(AdeverinteService adeverinteService) {
        this.adeverinteService = adeverinteService;
    }

    public List<AdeverintaStudentDto> getCereriInAsteptare() {
        return adeverinteService.getAdeverinteCuStatus(CerereStatus.PENDING);
    }


}
