package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.CerereStudentDto;
import ro.usv.certificates_generator.model.CerereStudent;
import ro.usv.certificates_generator.model.StudentExcel;
import ro.usv.certificates_generator.repository.CerereStudentRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdeverinteService {
    private final AuthService authService;
    private final StudentService studentService;
    private final CerereStudentRepository cerereStudentRepository;

    public CerereStudent procesareCerereStudent(CerereStudentDto request) {

        String email = authService.getEmail();
        String fullName = authService.getUserName();
        String[] nameParts = fullName.split("\\s+");
        String nume = nameParts[nameParts.length - 1];
        String prenume = extractPrenume(nameParts);
        String scop = request.scop();
        LocalDate dataCerere = LocalDate.now();
        CerereStudent cerereStudent = new CerereStudent(email,nume,prenume,dataCerere,scop);
        return cerereStudentRepository.save(cerereStudent);

    }

    private static String extractPrenume(String[] nameParts) {
        StringBuilder firstNameBuilder = new StringBuilder();
        for (int i = 0; i < nameParts.length - 1; i++) {
            if (i > 0) {
                firstNameBuilder.append(" "); // Add space separator between names
            }
            firstNameBuilder.append(nameParts[i]);
        }
        return firstNameBuilder.toString();
    }

    public StudentExcel getStudent(String email) {
        Optional<StudentExcel> student = studentService.getStudent(email);
        if (student.isPresent())
            return student.get();
        throw new UsernameNotFoundException("AdminService not found " + email);
    }
}
