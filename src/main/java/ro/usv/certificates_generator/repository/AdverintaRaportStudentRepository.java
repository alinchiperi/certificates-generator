package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.usv.certificates_generator.model.AdverintaRaportStudent;

import java.util.List;

public interface AdverintaRaportStudentRepository extends JpaRepository<AdverintaRaportStudent, Integer> {

    List<AdverintaRaportStudent> findByAnStudiu(String year);

}
