package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.Student;

@Repository
public interface StudentExcelRepository extends JpaRepository<Student, String> {
}
