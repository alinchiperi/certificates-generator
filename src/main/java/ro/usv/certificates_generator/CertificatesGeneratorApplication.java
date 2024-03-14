package ro.usv.certificates_generator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.usv.certificates_generator.model.User;
import ro.usv.certificates_generator.repository.UserRepository;

@SpringBootApplication
public class CertificatesGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificatesGeneratorApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(UserRepository userRepository) {
        return args -> {

            User admin = new User("alin.chiperi@student.usv.ro");
            userRepository.save(admin);

        };
    }

}
