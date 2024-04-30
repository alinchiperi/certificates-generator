package ro.usv.certificates_generator.controller;

import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.service.AdeverinteService;
import ro.usv.certificates_generator.service.SecretaraService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/secretara")
@RequiredArgsConstructor
public class SecretaraController {

    public final SecretaraService secretaraService;

    @GetMapping("/adeverinte/noi")
    public List<AdeverintaStudentDto> getAdeverinteInAsteptare() {
        return secretaraService.getCereriInAsteptare();
    }

    @PostMapping("/adeverinta/{idAdeverinta}/aproba")
    public void aprobaAdeverinta(@PathVariable("idAdeverinta") Integer idAdeverinta ) {
        secretaraService.aprobaAdeverinta(idAdeverinta);
    }

    @PostMapping("/numarInregistrare")
    public void setNumarInregistrare(@RequestParam ("numarInregistrare") Integer numarInregistrare) {
        secretaraService.setNumarInregistrare(numarInregistrare);
    }
    @GetMapping("/numarInregistrare")
    public int getNumarInregistrare() {
        return secretaraService.getNumarInregistrare();
    }


}
