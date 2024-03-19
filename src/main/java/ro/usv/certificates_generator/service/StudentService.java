package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.model.StudentExcel;
import ro.usv.certificates_generator.repository.StudentExcelRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentExcelRepository studentRepository;

    Optional<StudentExcel> getStudent(String email) {
        return studentRepository.findById(email);
    }

}
