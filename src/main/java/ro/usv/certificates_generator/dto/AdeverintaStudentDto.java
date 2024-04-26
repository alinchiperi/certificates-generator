package ro.usv.certificates_generator.dto;

import ro.usv.certificates_generator.model.CerereStudent;

import java.time.LocalDate;

public record AdeverintaStudentDto(
        int id,
        String emailStudent,
        String numeStudent,
        String prenumeStudent,
        LocalDate dataCerere,
        String scop
) {
    public static AdeverintaStudentDto fromCerereStudent(CerereStudent cerereStudent) {
        return new AdeverintaStudentDto(cerereStudent.getId(), cerereStudent.getEmailStudent(), cerereStudent.getNumeStudent(),
                cerereStudent.getPrenumeStudent(), cerereStudent.getDataCerere(), cerereStudent.getScop());

    }
}
