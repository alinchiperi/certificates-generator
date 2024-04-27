package ro.usv.certificates_generator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.usv.certificates_generator.model.Student;
import ro.usv.certificates_generator.repository.StudentExcelRepository;
import ro.usv.certificates_generator.service.AuthService;
import ro.usv.certificates_generator.service.FileService;

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



    @GetMapping("student")
    public ResponseEntity<Student> student() {
        Student student = repository.findById("alin.chiperi1@student.usv.ro").get();
        return ResponseEntity.ok(student);
    }

    @GetMapping("auth")
    public String testAuth() {
        if (authService.getUserName() != null) {
            return authService.getUserName();
        }
        return "Not found";
    }
}
