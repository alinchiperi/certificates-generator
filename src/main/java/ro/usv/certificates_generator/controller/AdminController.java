package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.usv.certificates_generator.dto.InformatiiFacultateDto;
import ro.usv.certificates_generator.model.InformatiiFacultate;
import ro.usv.certificates_generator.service.AdminService;

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


}
