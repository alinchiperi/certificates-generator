package ro.usv.certificates_generator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.CerereStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdeverintaStudentRepository extends JpaRepository<AdeverintaStudent, Integer> {

    List<AdeverintaStudent> findByAnUniversitar(String year);
    List<AdeverintaStudent> findAdeverintaStudentsByStatus(CerereStatus status);

    Page<AdeverintaStudent> findAdeverintaStudentsByStatus(CerereStatus status, Pageable pageable);

    Page<AdeverintaStudent> findAdeverintaStudentsByDataCerereBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<AdeverintaStudent> findAdeverintaStudentsByDataInregistrariiAndStatus(LocalDate date, CerereStatus status);
}
