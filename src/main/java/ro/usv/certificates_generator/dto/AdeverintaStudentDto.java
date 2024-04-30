package ro.usv.certificates_generator.dto;

import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.Student;

import java.time.LocalDate;

public record AdeverintaStudentDto(
        int id,
        String emailStudent,
        String numeStudent,
        LocalDate dataCerere,
        String scop
) {

    public static AdeverintaStudentDto fromAdeverintaStudent(AdeverintaStudent adeverintaStudent) {
        Student student = adeverintaStudent.getStudent();
        return new AdeverintaStudentDto(adeverintaStudent.getId(), student.getEmail(), student.getNumeComplet(), adeverintaStudent.getDataCerere(), adeverintaStudent.getScop());
    }
}
