package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.certificates_generator.dto.AddStudentiExcelResponse;
import ro.usv.certificates_generator.dto.InformatiiFacultateDto;
import ro.usv.certificates_generator.dto.SecretareDto;
import ro.usv.certificates_generator.model.InformatiiFacultate;
import ro.usv.certificates_generator.model.Secretara;
import ro.usv.certificates_generator.service.AdminService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/addInfoFacultate")
    public ResponseEntity<InformatiiFacultate> addInfoFacultate(@RequestBody InformatiiFacultateDto infoDto) {
        InformatiiFacultate informatiiFacultate = adminService.addInformatiiFacultate(infoDto);
        return ResponseEntity.ok(informatiiFacultate);
    }

    @PostMapping("/addSecretara")
    public ResponseEntity<Secretara> addSecreatara(@RequestBody SecretareDto secretareDto) {
        Secretara secretara = adminService.addSecretare(secretareDto);
        return ResponseEntity.ok(secretara);
    }

    @PostMapping("/addStudentiExcel")
    public ResponseEntity<AddStudentiExcelResponse> addStudentiExcel(@RequestParam("file") MultipartFile file) throws IOException {
        AddStudentiExcelResponse addStudentiExcelResponse = adminService.addStudentiExcel(file);
        return ResponseEntity.ok(addStudentiExcelResponse);
    }

    @PostMapping("/reset")
    public void reset() {
        adminService.reset();
    }


}
