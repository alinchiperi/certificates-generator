package ro.usv.certificates_generator.dto;

import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.Student;

import java.time.LocalDate;

public record AdeverintaAprobataDto(
        int id,
        String emailStudent,
        String numeStudent,
        LocalDate dataCerere,
        String scop,
        String numarInregistare
) {

    public static AdeverintaAprobataDto fromAdeverintaStudent(AdeverintaStudent adeverintaStudent) {
        Student student = adeverintaStudent.getStudent();
        return new AdeverintaAprobataDto(adeverintaStudent.getId(), student.getEmail(), student.getNumeComplet(), adeverintaStudent.getDataCerere(), adeverintaStudent.getScop(), adeverintaStudent.getNrInregistrare());
    }
}
