package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.CerereStatus;

import java.util.List;

@Repository
public interface AdeverintaStudentRepository extends JpaRepository<AdeverintaStudent, Integer> {

    List<AdeverintaStudent> findByAnUniversitar(String year);
    List<AdeverintaStudent> findAdeverintaStudentsByStatus(CerereStatus status);
}
