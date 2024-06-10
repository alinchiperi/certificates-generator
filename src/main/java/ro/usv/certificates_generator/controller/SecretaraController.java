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
import ro.usv.certificates_generator.dto.AdeverintaPrintareDto;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.dto.CerereStudentDto;
import ro.usv.certificates_generator.dto.RespingereCerereDto;
import ro.usv.certificates_generator.model.CerereStatus;
import ro.usv.certificates_generator.service.AdeverinteService;
import ro.usv.certificates_generator.service.EmailService;
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


    /**
     * Get all pending requests
     *
     * @return list of pending requests
     */
    @GetMapping("/adeverinte/noi/list")
    public List<AdeverintaStudentDto> getAdeverinteInAsteptare() {
        return secretaraService.getCereriByStatus(CerereStatus.PENDING);
    }

    /**
     * Retrieves a list of AdeverintaPrintareDto objects representing the certificates for print.
     *
     * @return a list of AdeverintaPrintareDto objects
     */

    @GetMapping("/adeverinte/printare/list")
    public List<AdeverintaPrintareDto> getAdeverintePrintare() {
        List<AdeverintaPrintareDto> adeverintePrintare = secretaraService.getAdeverintePrintare();
        return adeverintePrintare;
    }


    /**
     * Set the print status for the certificates to be printed.
     *
     * @param adeverinte list of AdeverintaPrintareDto objects to be printed
     */
    @PostMapping("/adeverinte/printare/print")
    public void setPrintAdeverinte(@RequestBody List<AdeverintaPrintareDto> adeverinte) {

        secretaraService.setPrintAdeverinte(adeverinte);
    }

    /**
     * Retrieves a page of pending AdeverintaStudentDto objects.
     *
     * @param page the page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a ResponseEntity containing the requested page of AdeverintaStudentDto objects
     */

    @GetMapping("/adeverinte/noi/page")
    public ResponseEntity<Page<AdeverintaStudentDto>> getAdeverintePandingPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<AdeverintaStudentDto> adeverinte = adeverinteService.findAdeverintaStudentsPending(page, size);
        return ResponseEntity.ok(adeverinte);
    }

    /**
     * Retrieves a list of AdeverintaStudentDto objects within a specified date range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @param page      the page number (default is 0)
     * @param size      the number of items per page (default is 10)
     * @return a ResponseEntity containing the requested page of AdeverintaStudentDto objects
     */

    @GetMapping("/adeverinte/noi/between")
    public ResponseEntity<Page<AdeverintaStudentDto>> getAdeverinteNoiBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<AdeverintaStudentDto> adeverinte = adeverinteService.findAdeverinteBetweenDates(startDate, endDate, page, size);
        return ResponseEntity.ok(adeverinte);
    }

    /**
     * Retrieves a paginated list of AdeverintaAprobataDto objects within a specified date range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @param page      the page number (default is 0)
     * @param size      the number of items per page (default is 10)
     * @return a ResponseEntity containing the requested page of AdeverintaAprobataDto objects
     */
    @GetMapping("/adeverinte/aprobate/between")
    public ResponseEntity<Page<AdeverintaAprobataDto>> getAdeverinteAprobateBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<AdeverintaAprobataDto> adeverinte = adeverinteService.findAdeverinteAprobateBetweenDates(startDate, endDate, page, size);
        return ResponseEntity.ok(adeverinte);
    }

    /**
     * Retrieves a paginated list of AdeverintaAprobataDto objects for approved students.
     *
     * @param page the page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @return a ResponseEntity containing the requested page of AdeverintaAprobataDto objects
     */

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

    /**
     * Aproves a certificate based on the provided ID.
     *
     * @param idAdeverinta the ID of the certificate to be approved
     */

    @PostMapping("/adeverinta/{idAdeverinta}/aproba")
    public void aprobaAdeverinta(@PathVariable("idAdeverinta") Integer idAdeverinta) {
        secretaraService.aprobaAdeverinta(idAdeverinta);
    }

    /**
     * Sets the number of registrations for the certificates
     *
     * @param numarInregistrare the new number of registrations
     */

    @PostMapping("/numarInregistrare")
    public void setNumarInregistrare(@RequestParam("numarInregistrare") Integer numarInregistrare) {
        secretaraService.setNumarInregistrare(numarInregistrare);
    }

    /**
     * Retrieves the number of registrations for the certificates.
     *
     * @return the number of registrations
     */

    @GetMapping("/numarInregistrare")
    public int getNumarInregistrare() {
        return secretaraService.getNumarInregistrare();
    }

    /**
     * Reject a request with the specified ID using the given rejection reason.
     *
     * @param  idCerere  the ID of the request to be rejected
     * @param  motiv     the rejection reason provided
     */

    @PostMapping("/adeverinta/{id}/respinge")
    public void respingeCerere(@PathVariable("id") Integer idCerere, @RequestBody RespingereCerereDto motiv) {
        secretaraService.respingeCerere(idCerere, motiv.motiv());
    }

    /**
     * Update the scope of a request with the specified ID.
     *
     * @param  idCerere  the ID of the request to be updated
     * @param  scop      the updated scope for the request
     */

    @PatchMapping("/adeverinta/{id}/update/scop")
    public void updateScop(@PathVariable("id") Integer idCerere, @RequestBody CerereStudentDto scop) {
        secretaraService.updateScopCerere(idCerere, scop.scop());
    }

    /**
     * Generates a report for the secretary
     *
     * @return a ResponseEntity containing the generated report
     */

    @GetMapping("/raport")
    public ResponseEntity<byte[]> generateReport() {

        List<AdeverintaAprobataDto> adeverinte = adeverinteService.getAdeverinteAprobateForDateWithStatus(LocalDate.now(), CerereStatus.APPROVED);

        byte[] raport = fileService.generateRaportForSecreatara(adeverinte);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "Raport.xlsx");
        headers.setContentLength(raport.length);

        return new ResponseEntity<>(raport, headers, HttpStatus.OK);

    }
}
