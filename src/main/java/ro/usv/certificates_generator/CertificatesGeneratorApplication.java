package ro.usv.certificates_generator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.usv.certificates_generator.model.Admin;
import ro.usv.certificates_generator.model.Secretara;
import ro.usv.certificates_generator.repository.AdminRepository;
import ro.usv.certificates_generator.repository.SecretaraRepository;

@SpringBootApplication
public class CertificatesGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificatesGeneratorApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AdminRepository adminRepository, PasswordEncoder passwordEncoder, SecretaraRepository secretaraRepository) {
        return args -> {

            if (adminRepository.findByUsername("admin").isEmpty()) {
                Admin admin = new Admin("admin", passwordEncoder.encode("admin"));
                adminRepository.save(admin);

            }
            if (secretaraRepository.findByEmail("chiperialin@gmail.com").isEmpty()) {
                Secretara secretara = new Secretara("Chiperi", "Alin", "ing", "chiperialin@gmail.com");
                secretaraRepository.save(secretara);
            }

        };
    }
}
