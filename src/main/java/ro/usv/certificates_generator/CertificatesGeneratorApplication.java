package ro.usv.certificates_generator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.usv.certificates_generator.model.Admin;
import ro.usv.certificates_generator.repository.AdminRepository;

@SpringBootApplication
public class CertificatesGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificatesGeneratorApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            Admin admin = new Admin("admin", passwordEncoder.encode("admin"));
            adminRepository.save(admin);

        };
    }
}
