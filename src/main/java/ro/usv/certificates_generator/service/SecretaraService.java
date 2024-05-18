package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.AdeverintaAprobataDto;
import ro.usv.certificates_generator.dto.AdeverintaPrintareDto;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.model.AdeverintaRespinsa;
import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.CerereStatus;
import ro.usv.certificates_generator.model.InformatiiFacultate;
import ro.usv.certificates_generator.model.Secretara;
import ro.usv.certificates_generator.model.Student;
import ro.usv.certificates_generator.repository.AdeverintaStudentRepository;
import ro.usv.certificates_generator.repository.AdeverinteRespinseRepository;
import ro.usv.certificates_generator.repository.SecretaraRepository;
import ro.usv.certificates_generator.service.manager.NumarOrdineManager;
import ro.usv.certificates_generator.service.manager.NumarInregistrareManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecretaraService {

    private final AdeverinteService adeverinteService;
    private final NumarInregistrareManager numarInregistrareManager;
    private final AdeverintaStudentRepository adeverintaStudentRepository;
    private final NumarOrdineManager numarOrdineManager;
    private final AdeverinteRespinseRepository adeverinteRespinseRepository;
    private final AuthService authService;
    private final SecretaraRepository secretaraRepository;
    private final EmailService emailService;
    private final FileService fileService;
    private final InformatiiFacultateService informatiiFacultateService;


    public List<AdeverintaStudentDto> getCereriByStatus(CerereStatus cerereStatus) {
        return adeverinteService.getAdeverinteCuStatus(cerereStatus);
    }


    public void setNumarInregistrare(Integer numarInregistrare) {
        log.info("set numar iregisrare");
        LocalDate now = LocalDate.now();
        if (numarInregistrareManager.getNumarInregistrare(now) == 0) {
            numarInregistrareManager.setNumarInregisrare(now, numarInregistrare);
        }
    }

    public int getNumarInregistrare() {
        return numarInregistrareManager.getNumarInregistrare(LocalDate.now());
    }

    public void aprobaAdeverinta(Integer idAdeverinta) {

        Optional<AdeverintaStudent> optionalAdeverintaStudent = adeverintaStudentRepository.findById(idAdeverinta);
        if (optionalAdeverintaStudent.isEmpty()) {
            throw new IllegalArgumentException("Adeverinta cu id-ul " + idAdeverinta + " nu exista");
        } else {

            int numarInregistrare = numarInregistrareManager.getNumarInregistrare(LocalDate.now());
            if (numarInregistrare == 0) {
                throw new IllegalArgumentException("Numarul de inregistrare nu este setat");
            }

            AdeverintaStudent adeverintaStudent = optionalAdeverintaStudent.get();
            LocalDate dataInregistrarii = LocalDate.now();
            int numarAdeverinta = numarOrdineManager.getNumarAdeverinta();

            adeverintaStudent.setDataInregistrarii(dataInregistrarii);
            adeverintaStudent.setStatus(CerereStatus.APPROVED);
            adeverintaStudent.setNumarInregistrare(numarInregistrare);
            adeverintaStudent.setNumarOrdine(numarAdeverinta);

            adeverintaStudentRepository.save(adeverintaStudent);

            sendRidicareAdeverintaToStudent(adeverintaStudent);

        }

    }

    private void sendRidicareAdeverintaToStudent(AdeverintaStudent adeverintaStudent) {
        String emailStudent = adeverintaStudent.getStudent().getEmail();
        LocalDate data = LocalDate.now();
        if (data.getDayOfWeek() == DayOfWeek.FRIDAY) {
            data = data.plusDays(3);
        } else {
            data = data.plusDays(1);
        }
        String text = "Puteți ridica adeverința de la secretariat începând cu data de " + data + ", în\n" +
                "perioada programului de lucru al secretariatului";

        emailService.sendEmailWithMessage(emailStudent, "Ridicare adeverinta", text);
    }

    public List<AdeverintaAprobataDto> getCereriAprobate() {
        List<AdeverintaStudent> adeverintaStudents = adeverintaStudentRepository.findAdeverintaStudentsByStatus(CerereStatus.APPROVED);

        return adeverintaStudents.stream().map(AdeverintaAprobataDto::fromAdeverintaStudent).toList();

    }

    public Secretara getCurrentSecretara() {
        String email = authService.getEmail();
        Optional<Secretara> optionalSecretara = secretaraRepository.findByEmail(email);
        if (optionalSecretara.isPresent())
            return optionalSecretara.get();
        else {
            throw new IllegalArgumentException("Nu exista secretara cu emailul " + email);
        }
    }

    public void respingeCerere(Integer idCerere, String motiv) {

        Optional<AdeverintaStudent> optionalAdeverintaStudent = adeverintaStudentRepository.findById(idCerere);
        if (optionalAdeverintaStudent.isEmpty()) {
            throw new IllegalArgumentException("Adeverinta cu id-ul " + idCerere + " nu exista");
        } else {


            AdeverintaStudent adeverintaStudent = optionalAdeverintaStudent.get();
            adeverintaStudent.setStatus(CerereStatus.REJECTED);
            AdeverintaRespinsa adeverintaRespinsa = new AdeverintaRespinsa();
            adeverintaRespinsa.setAdeverintaStudent(adeverintaStudent);
            adeverintaRespinsa.setMotiv(motiv);
            adeverintaRespinsa.setData(LocalDate.now());
            adeverintaRespinsa.setSecretara(getCurrentSecretara());
            adeverinteRespinseRepository.save(adeverintaRespinsa);
            adeverintaStudentRepository.save(adeverintaStudent);

            String text = "Cererea dumneavoastra a fost respinsă cu motivul: " + motiv;
            emailService.sendEmailWithMessage(adeverintaStudent.getStudent().getEmail(), "Cerere respinsă", text);

        }
    }

    public void updateScopCerere(Integer idCerere, String scop) {
        Optional<AdeverintaStudent> adeverintaStudent = adeverintaStudentRepository.findById(idCerere);
        if (adeverintaStudent.isEmpty()) {
            throw new IllegalArgumentException("Adeverinta cu id-ul " + idCerere + " nu exista");
        } else {
            adeverintaStudent.get().setScop(scop);
            adeverintaStudentRepository.save(adeverintaStudent.get());
        }
    }

    public List<Secretara> getSecretare() {
        return secretaraRepository.findAll();
    }

    @Scheduled(cron = "0 0 16 ? * MON-FRI")
    public void sendReportEmail() {
        List<AdeverintaAprobataDto> adeverinte = adeverinteService.getAdeverinteAprobateForDateWithStatus(LocalDate.now(), CerereStatus.APPROVED);
        byte[] raport = fileService.generateRaportForSecreatara(adeverinte);
        List<Secretara> secretare = getSecretare();
        String subject = "Raport " + LocalDate.now();
        String text = "Raportul a fost generat pe data de " + LocalDate.now();
        String attachmentName = "raport.xlsx";

        for (Secretara secretara : secretare) {
            String email = secretara.getEmail();
            emailService.sendEmailWithAttachment(email, subject, text, raport, attachmentName);
        }
    }

    public List<AdeverintaPrintareDto> getAdeverintePrintare() {
        List<AdeverintaPrintareDto> adeverinte = new ArrayList<>();
        Secretara secretara = getCurrentSecretara();
        InformatiiFacultate informatiiFacultate = informatiiFacultateService.getInformatiiFacultate();

        List<AdeverintaStudent> adeverinteStudentsByStatusAndPrinted = adeverintaStudentRepository.findAdeverintaStudentsByStatusAndIsPrinted(CerereStatus.APPROVED, false);

        log.info("adeverinte: {}", adeverinteStudentsByStatusAndPrinted);

        for (AdeverintaStudent adeverintaStudent : adeverinteStudentsByStatusAndPrinted) {
            AdeverintaPrintareDto adv = getAdeverintaPrintareDto(adeverintaStudent, informatiiFacultate, secretara);

            adeverinte.add(adv);
        }

        return adeverinte;
    }

    private static AdeverintaPrintareDto getAdeverintaPrintareDto(AdeverintaStudent adeverintaStudent, InformatiiFacultate informatiiFacultate, Secretara secretara) {
        Student student = adeverintaStudent.getStudent();
        AdeverintaPrintareDto adv = new AdeverintaPrintareDto(
                adeverintaStudent.getId(),
                adeverintaStudent.getNrInregistrare(),
                student.getNumeComplet(),
                student.getAnStudiu(),
                student.getProgramStudiu(),
                student.getDomeniuStudiu(),
                student.getFinantare(),
                informatiiFacultate.getNumeDecan(),
                informatiiFacultate.getNumeSecretarSef(),
                adeverintaStudent.getScop(),
                secretara.getNumeComplet());
        return adv;
    }

    public void setPrintAdeverinte(List<AdeverintaPrintareDto> adeverinte) {
        for (AdeverintaPrintareDto adeverintaPrintareDto : adeverinte) {
            Integer nrInregistrare = adeverintaPrintareDto.id();
            Optional<AdeverintaStudent> optionalAdeverintaStudent = adeverintaStudentRepository.findById(nrInregistrare);
            if (optionalAdeverintaStudent.isEmpty()) {
                throw new IllegalArgumentException("Adeverinta cu id-ul " + nrInregistrare + " nu exista");
            } else {
                optionalAdeverintaStudent.get().setPrinted(true);
                adeverintaStudentRepository.save(optionalAdeverintaStudent.get());
            }
        }
    }
}
