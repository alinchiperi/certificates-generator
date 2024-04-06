package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.InformatiiFacultate;

@Repository
public interface InformatiiFacultateRepository extends JpaRepository<InformatiiFacultate, Integer> {
}
