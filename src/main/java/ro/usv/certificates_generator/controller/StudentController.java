package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.usv.certificates_generator.dto.CerereStudentDto;
import ro.usv.certificates_generator.service.AdeverinteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adeverinta")
public class StudentController {

    private final AdeverinteService adeverinteService;

    /**
     * A method to generate a certificate based on a student request.
     *
     * @param  request   the CerereStudentDto object containing the student request data
     */
    @PostMapping("student")
    public void generateCertificate(@RequestBody CerereStudentDto request) {
        adeverinteService.procesareCerereStudent(request);
    }
}
