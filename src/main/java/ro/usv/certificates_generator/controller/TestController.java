package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.usv.certificates_generator.model.StudentExcel;
import ro.usv.certificates_generator.repository.StudentExcelRepository;
import ro.usv.certificates_generator.service.AuthService;
import ro.usv.certificates_generator.service.FileService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class TestController {
    private final FileService fileService;
    private final StudentExcelRepository repository;
    private final AuthService authService;

    @GetMapping("hello")
    public String helloDocker() {
        return "Hello docker";
    }

    @GetMapping("students")
    public ResponseEntity<List<StudentExcel>> getStudents() {
        List<StudentExcel> students = new ArrayList<>();
        try {
            students = fileService.loadStudentsFromExcel("studenti.xlsx");
        } catch (Exception e) {
            log.error("Exeption: " + e.getMessage());
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("student")
    public ResponseEntity<StudentExcel> student() {
        StudentExcel studentExcel = repository.findById("alin.chiperi1@student.usv.ro").get();
        return ResponseEntity.ok(studentExcel);
    }

    @GetMapping("auth")
    public String testAuth() {
        if (authService.getUserName() != null) {
            return authService.getUserName();
        }
        return "Not found";
    }
}
