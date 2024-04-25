package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ResponseEntity<InformatiiFacultateDto> addInfoFacultate(@RequestBody InformatiiFacultateDto infoDto) {
        InformatiiFacultate informatiiFacultate = adminService.addInformatiiFacultate(infoDto);
        return ResponseEntity.ok(InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate));
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

    @PatchMapping("/update/numeFacultate")
    public ResponseEntity<InformatiiFacultateDto> updateNumeFaculatate(@RequestParam("numeFacultate") String numeFacultate) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateNumeFacultate(numeFacultate);
        return ResponseEntity.ok(informatiiFacultateDto);
    }
    @PatchMapping("/update/numeDecan")
    public ResponseEntity<InformatiiFacultateDto> updateNumeDecan(@RequestParam("numeDecan") String numeDecan) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateNumeDecan(numeDecan);
        return ResponseEntity.ok(informatiiFacultateDto);
    }
    @PatchMapping("/update/numeSecretaraSef")
    public ResponseEntity<InformatiiFacultateDto> updateNumeSecretaraSef(@RequestParam("numeSecretaraSef") String numeSeretaraSef) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateNumeSecretaraSef(numeSeretaraSef);
        return ResponseEntity.ok(informatiiFacultateDto);
    }
    @PatchMapping("/update/prescurtareFacultate")
    public ResponseEntity<InformatiiFacultateDto> updatePrescurtareFacultate(@RequestParam("prescurtareFacultate") String prescurtareFacultate) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updatePrescurtareFacultate(prescurtareFacultate);
        return ResponseEntity.ok(informatiiFacultateDto);
    }
    @PatchMapping("/update/anUniversitar")
    public ResponseEntity<InformatiiFacultateDto> updateAnUniversitar(@RequestParam("anUniversitar") String anUniversitar) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateAnUniversitar(anUniversitar);
        return ResponseEntity.ok(informatiiFacultateDto);
    }

    @GetMapping("/getInfoFacultate")
    public ResponseEntity<InformatiiFacultateDto> getInformatiiFacultate() {

        InformatiiFacultate informatiiFacultate = adminService.getInformatiiFacultate();
        if (informatiiFacultate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate));
    }


}
