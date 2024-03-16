package ro.usv.certificates_generator.repository;

import org.springframework.data.repository.CrudRepository;
import ro.usv.certificates_generator.model.StudentExcel;

public interface StudentExcelRepository extends CrudRepository<StudentExcel, String> {
}
