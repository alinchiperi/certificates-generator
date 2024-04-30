package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.Secretara;

import java.util.Optional;

@Repository
public interface SecretaraRepository extends JpaRepository<Secretara, Integer> {

    Optional<Secretara> findByEmail(String email);
}
