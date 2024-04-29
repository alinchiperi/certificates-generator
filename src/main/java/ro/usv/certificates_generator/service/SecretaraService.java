package ro.usv.certificates_generator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.model.CerereStatus;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class SecretaraService {

    private final AdeverinteService adeverinteService;
    private final NumarAdeverinteManager numarAdeverinteManager;

    public SecretaraService(AdeverinteService adeverinteService, NumarAdeverinteManager numarAdeverinteManager) {
        this.adeverinteService = adeverinteService;
        this.numarAdeverinteManager = numarAdeverinteManager;
    }

    public List<AdeverintaStudentDto> getCereriInAsteptare() {
        return adeverinteService.getAdeverinteCuStatus(CerereStatus.PENDING);
    }


    public void setNumarInregistrare(Integer numarInregistrare) {
        log.info("set numar iregisrare");
        LocalDate now = LocalDate.now();
        if (numarAdeverinteManager.getNumarAdeverinte(now) == 0) {
            numarAdeverinteManager.setNumarAdeverinte(now, numarInregistrare);
        }
    }

    public int getNumarInregistrare() {
        return numarAdeverinteManager.getNumarAdeverinte(LocalDate.now());
    }
}
