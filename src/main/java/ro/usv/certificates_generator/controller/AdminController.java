package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.certificates_generator.dto.AddStudentiExcelResponse;
import ro.usv.certificates_generator.dto.InformatiiFacultateDto;
import ro.usv.certificates_generator.dto.SecretaraDto;
import ro.usv.certificates_generator.dto.StudentDto;
import ro.usv.certificates_generator.model.InformatiiFacultate;
import ro.usv.certificates_generator.model.Secretara;
import ro.usv.certificates_generator.service.AdminService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     * Adds information about the faculty.
     *
     * @param infoDto The DTO containing information about the faculty
     * @return The response entity with the DTO of the added faculty information
     */
    @PostMapping("/addInfoFacultate")
    public ResponseEntity<InformatiiFacultateDto> addInfoFacultate(@RequestBody InformatiiFacultateDto infoDto) {
        InformatiiFacultate informatiiFacultate = adminService.addInformatiiFacultate(infoDto);
        return ResponseEntity.ok(InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate));
    }

    /**
     * Adds a new secretary to the system.
     *
     * @param secretaraDto the secretary data transfer object containing the information of the new secretara
     * @return a ResponseEntity containing the newly added secretary, with a status code of 200 (OK)
     */
    @PostMapping("/addSecretara")
    public ResponseEntity<Secretara> addSecreatara(@RequestBody SecretaraDto secretaraDto) {
        Secretara secretara = adminService.addSecretare(secretaraDto);
        return ResponseEntity.ok(secretara);
    }

    /**
     * Adds students information from an Excel file.
     *
     * @param file The Excel file containing students information
     * @return The response entity with the result of adding student information
     */
    @PostMapping("/addStudentiExcel")
    public ResponseEntity<AddStudentiExcelResponse> addStudentiExcel(@RequestParam("file") MultipartFile file) throws IOException {
        AddStudentiExcelResponse addStudentiExcelResponse = adminService.addStudentiExcel(file);
        return ResponseEntity.ok(addStudentiExcelResponse);
    }

    /**
     * Resets the system.
     */
    @PostMapping("/reset")
    public void reset() {
        adminService.reset();
    }

    /**
     * Updates the name of the faculty.
     *
     * @param numeFacultate the new name of the faculty
     * @return a ResponseEntity containing the updated InformatiiFacultateDto
     */
    @PatchMapping("/update/numeFacultate")
    public ResponseEntity<InformatiiFacultateDto> updateNumeFaculatate(@RequestParam("numeFacultate") String numeFacultate) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateNumeFacultate(numeFacultate);
        return ResponseEntity.ok(informatiiFacultateDto);
    }

    /**
     * Updates the name of the dean.
     *
     * @param numeDecan the new name of the dean
     * @return a ResponseEntity containing the updated InformatiiFacultateDto
     */
    @PatchMapping("/update/numeDecan")
    public ResponseEntity<InformatiiFacultateDto> updateNumeDecan(@RequestParam("numeDecan") String numeDecan) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateNumeDecan(numeDecan);
        return ResponseEntity.ok(informatiiFacultateDto);
    }

    /**
     * Updates the name of the primary secretary.
     *
     * @param numeSeretaraSef the new name of the primary secretary
     * @return a ResponseEntity containing the updated InformatiiFacultateDto
     */
    @PatchMapping("/update/numeSecretaraSef")
    public ResponseEntity<InformatiiFacultateDto> updateNumeSecretaraSef(@RequestParam("numeSecretaraSef") String numeSeretaraSef) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateNumeSecretaraSef(numeSeretaraSef);
        return ResponseEntity.ok(informatiiFacultateDto);
    }

    /**
     * Updates the abbreviation of the faculty.
     *
     * @param prescurtareFacultate the new abbreviation of the faculty
     * @return a ResponseEntity containing the updated InformatiiFacultateDto
     */
    @PatchMapping("/update/prescurtareFacultate")
    public ResponseEntity<InformatiiFacultateDto> updatePrescurtareFacultate(@RequestParam("prescurtareFacultate") String prescurtareFacultate) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updatePrescurtareFacultate(prescurtareFacultate);
        return ResponseEntity.ok(informatiiFacultateDto);
    }

    /**
     * Updates the year of the university.
     *
     * @param anUniversitar the new year of the university
     * @return a ResponseEntity containing the updated InformatiiFacultateDto
     */
    @PatchMapping("/update/anUniversitar")
    public ResponseEntity<InformatiiFacultateDto> updateAnUniversitar(@RequestParam("anUniversitar") String anUniversitar) {
        InformatiiFacultateDto informatiiFacultateDto = adminService.updateAnUniversitar(anUniversitar);
        return ResponseEntity.ok(informatiiFacultateDto);
    }

    /**
     * Retrieves information about the faculty.
     *
     * @return ResponseEntity containing the InformatiiFacultateDto object
     */

    @GetMapping("/getInfoFacultate")
    public ResponseEntity<InformatiiFacultateDto> getInformatiiFacultate() {

        InformatiiFacultate informatiiFacultate = adminService.getInformatiiFacultate();
        if (informatiiFacultate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate));
    }

    /**
     * Retrieves a list of secretaries.
     *
     * @return a ResponseEntity containing the list of SecretaraDto objects
     */

    @GetMapping("/secretare")
    public ResponseEntity<List<SecretaraDto>> getSecretare() {
        return ResponseEntity.ok(adminService.getSecretare());
    }

    /**
     * Retrieves a list of students.
     *
     * @return a ResponseEntity containing the list of StudentDto objects
     */
    @GetMapping("/studenti")
    public ResponseEntity<List<StudentDto>> getStudents() {
        return ResponseEntity.ok(adminService.getStudents());
    }

    /**
     * Deletes a secretary with the given id.
     *
     * @param id the id of the secretary to be deleted
     */
    @DeleteMapping("/secretare/{id}")
    public void deleteSecretara(@PathVariable int id) {
        adminService.deleteSecretara(id);
    }


    /**
     * Deletes a student with the given email.
     *
     * @param email the email of the student to be deleted
     */

    @DeleteMapping("/studenti/{email}")
    public void deleteStudent(@PathVariable String email) {
        adminService.deleteStudent(email);
    }

    /**
     * Updates a secretary with the given id and new data.
     * @param id the id of the secretary to be updated
     * @param secretaraDto  the new data for the secretary
     * @return  a ResponseEntity containing the updated SecretaraDto object
     */

    @PutMapping("/update/secretara/{id}")
    public SecretaraDto updateSecretara(@PathVariable int id, @RequestBody SecretaraDto secretaraDto) {
        Secretara secretara = adminService.updateSecretara(id, secretaraDto);
        return SecretaraDto.fromSecretara(secretara);
    }

}
