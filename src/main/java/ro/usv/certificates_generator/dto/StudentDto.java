package ro.usv.certificates_generator.dto;


import ro.usv.certificates_generator.model.Student;

public record StudentDto(
    String email,
    String programStudiu,
    String cicluStudiu,
    String domeniuStudiu,
    String anStudiu,
    String formaInvatamant,
    String finantare,
    String numeComplet,
    char sex
) {
    public static StudentDto fromStudent(Student student) {
        return new StudentDto(
            student.getEmail(),
            student.getProgramStudiu(),
            student.getCicluStudiu(),
            student.getDomeniuStudiu(),
            student.getAnStudiu(),
            student.getFormaInvatamant(),
            student.getFinantare(),
            student.getNumeComplet(),
            student.getSex()
        );
    }
}
