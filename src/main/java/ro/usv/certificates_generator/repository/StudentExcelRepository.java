package ro.usv.certificates_generator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.StudentExcel;

@Repository
public interface StudentExcelRepository extends CrudRepository<StudentExcel, String> {
}
