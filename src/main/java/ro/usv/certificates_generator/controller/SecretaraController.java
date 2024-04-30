package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.usv.certificates_generator.dto.AdeverintaAprobataDto;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.dto.CerereStudentDto;
import ro.usv.certificates_generator.dto.RespingereCerereDto;
import ro.usv.certificates_generator.model.CerereStatus;
import ro.usv.certificates_generator.service.AdeverinteService;
import ro.usv.certificates_generator.service.FileService;
import ro.usv.certificates_generator.service.SecretaraService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/secretara")
@RequiredArgsConstructor
public class SecretaraController {

    public final SecretaraService secretaraService;
    public final AdeverinteService adeverinteService;
    private final FileService fileService;

    @GetMapping("/adeverinte/noi/list")
    public List<AdeverintaStudentDto> getAdeverinteInAsteptare() {
        return secretaraService.getCereriByStatus(CerereStatus.PENDING);
    }

    @GetMapping("/adeverinte/noi/page")
    public ResponseEntity<Page<AdeverintaStudentDto>> getAdeverintePandingPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size) {


        Page<AdeverintaStudentDto> adeverinte = adeverinteService.findAdeverintaStudentsPending(page, size);
        return ResponseEntity.ok(adeverinte);
    }

    @GetMapping("/adeverinte/noi/between")
    public ResponseEntity<Page<AdeverintaStudentDto>> getAdeverinteNoiBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<AdeverintaStudentDto> adeverinte = adeverinteService.findAdeverinteBetweenDates(startDate, endDate, page, size);
        return ResponseEntity.ok(adeverinte);
    }

    @GetMapping("/adeverinte/aprobate/between")
    public ResponseEntity<Page<AdeverintaAprobataDto>> getAdeverinteAprobateBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<AdeverintaAprobataDto> adeverinte = adeverinteService.findAdeverinteAprobateBetweenDates(startDate, endDate, page, size);
        return ResponseEntity.ok(adeverinte);
    }

    @GetMapping("/adeverinte/aprobate/page")
    public ResponseEntity<Page<AdeverintaAprobataDto>> getAdeverinteAprobatePage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<AdeverintaAprobataDto> adeverinte = adeverinteService.findAdeverintaStudentsApproved(page, size);
        return ResponseEntity.ok(adeverinte);
    }

    @GetMapping("/adeverinte/aprobate/list")
    public List<AdeverintaAprobataDto> getAdeverinteAprobate() {

        return secretaraService.getCereriAprobate();
    }

    @PostMapping("/adeverinta/{idAdeverinta}/aproba")
    public void aprobaAdeverinta(@PathVariable("idAdeverinta") Integer idAdeverinta) {
        secretaraService.aprobaAdeverinta(idAdeverinta);
    }

    @PostMapping("/numarInregistrare")
    public void setNumarInregistrare(@RequestParam("numarInregistrare") Integer numarInregistrare) {
        secretaraService.setNumarInregistrare(numarInregistrare);
    }

    @GetMapping("/numarInregistrare")
    public int getNumarInregistrare() {
        return secretaraService.getNumarInregistrare();
    }

    @PostMapping("/adeverinta/{id}/respinge")
    public void respingeCerere(@PathVariable("id") Integer idCerere, @RequestBody RespingereCerereDto motiv) {
        secretaraService.respingeCerere(idCerere, motiv.motiv());
    }

    @PatchMapping("/adeverinta/{id}/update/scop")
    public void updateScop(@PathVariable("id") Integer idCerere, @RequestBody CerereStudentDto scop) {
        secretaraService.updateScopCerere(idCerere, scop.scop());
    }

    @GetMapping("/raport")
    public ResponseEntity<byte[]> generateReport(){

        List<AdeverintaAprobataDto> adeverinte = adeverinteService.getAdeverinteAprobateForDateWithStatus(LocalDate.now(), CerereStatus.APPROVED);

        byte[] raport = fileService.generateRaportForSecreatara(adeverinte);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "CertificateReport.xlsx");
        headers.setContentLength(raport.length);

        return new ResponseEntity<>(raport, headers, HttpStatus.OK);

    }
}
