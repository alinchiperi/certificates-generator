package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.CerereStudent;

@Repository
public interface CerereStudentRepository extends JpaRepository<CerereStudent, Integer> {
}
