package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.usv.certificates_generator.model.AdverintaStudent;

import java.util.List;

public interface AdverintaStudentRepository extends JpaRepository<AdverintaStudent, Integer> {

    List<AdverintaStudent> findByAnUniversitar(String year);
}
