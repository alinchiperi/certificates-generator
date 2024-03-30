package ro.usv.certificates_generator.dto;

import ro.usv.certificates_generator.model.StudentExcel;

import java.util.List;

public record AddStudentiExcelResponse(List<StudentExcel> successStudents, List<StudentExcel> failsStudents) {

}
