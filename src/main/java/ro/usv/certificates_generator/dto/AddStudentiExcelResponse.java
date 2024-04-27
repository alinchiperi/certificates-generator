package ro.usv.certificates_generator.dto;

import ro.usv.certificates_generator.model.Student;

import java.util.List;

public record AddStudentiExcelResponse(List<Student> successStudents, List<Student> failsStudents) {

}
