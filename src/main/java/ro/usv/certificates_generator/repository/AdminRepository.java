package ro.usv.certificates_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ro.usv.certificates_generator.model.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Optional<Admin> findByUsername(String username);
}
