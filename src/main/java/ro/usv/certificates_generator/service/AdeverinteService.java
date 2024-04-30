package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.AdeverintaStudentDto;
import ro.usv.certificates_generator.dto.CerereStudentDto;
import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.CerereStatus;
import ro.usv.certificates_generator.model.Student;
import ro.usv.certificates_generator.repository.AdeverintaStudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdeverinteService {
    private final AuthService authService;
    private final StudentService studentService;


    private final AdeverintaStudentRepository adeverintaStudentRepository;

    public void procesareCerereStudent(CerereStudentDto request) {

        String email = authService.getEmail();

        Student student = getStudent(email);
        String scop = request.scop();
        LocalDate dataCerere = LocalDate.now();
        AdeverintaStudent adeverintaStudent = new AdeverintaStudent(student, scop, dataCerere);
        adeverintaStudentRepository.save(adeverintaStudent);

    }


    public Student getStudent(String email) {
        Optional<Student> student = studentService.getStudent(email);
        if (student.isPresent())
            return student.get();
        throw new UsernameNotFoundException("Student not found " + email);
    }

    public List<AdeverintaStudentDto> getAdeverinteCuStatus(CerereStatus status) {
        List<AdeverintaStudent> adeverintaStudentByStatus = adeverintaStudentRepository.findAdeverintaStudentsByStatus(status);

        return adeverintaStudentByStatus.stream().map(AdeverintaStudentDto::fromAdeverintaStudent).toList();
    }
}
