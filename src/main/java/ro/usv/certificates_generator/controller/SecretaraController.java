package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.service.AdeverinteService;
import ro.usv.certificates_generator.service.SecretaraService;

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

}
