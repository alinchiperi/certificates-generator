package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.CerereStatus;
import ro.usv.certificates_generator.repository.AdeverintaStudentRepository;
import ro.usv.certificates_generator.service.manager.NumarAdeverinteManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecretaraService {

    private final AdeverinteService adeverinteService;
    private final NumarAdeverinteManager numarAdeverinteManager;
    private final AdeverintaStudentRepository adeverintaStudentRepository;


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

    public void aprobaAdeverinta(Integer idAdeverinta) {

        Optional<AdeverintaStudent> optionalAdeverintaStudent = adeverintaStudentRepository.findById(idAdeverinta);
        if (optionalAdeverintaStudent.isEmpty()) {
            throw new IllegalArgumentException("Adeverinta cu id-ul " + idAdeverinta + " nu exista");
        }else {

            AdeverintaStudent adeverintaStudent = optionalAdeverintaStudent.get();

            adeverintaStudent.setStatus(CerereStatus.APPROVED);
            adeverintaStudentRepository.save(adeverintaStudent);
        }



    }
}
