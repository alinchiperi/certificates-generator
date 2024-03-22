package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.usv.certificates_generator.model.InformatiiFacultate;

public interface InformatiiFacultateRepository extends JpaRepository<InformatiiFacultate, Integer> {
}
