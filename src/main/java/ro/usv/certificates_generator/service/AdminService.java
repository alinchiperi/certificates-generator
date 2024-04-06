package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.certificates_generator.dto.AddStudentiExcelResponse;
import ro.usv.certificates_generator.dto.InformatiiFacultateDto;
import ro.usv.certificates_generator.dto.SecretareDto;
import ro.usv.certificates_generator.model.InformatiiFacultate;
import ro.usv.certificates_generator.model.Secretara;
import ro.usv.certificates_generator.repository.AdminRepository;
import ro.usv.certificates_generator.repository.InformatiiFacultateRepository;
import ro.usv.certificates_generator.repository.SecretaraRepository;
import ro.usv.certificates_generator.repository.StudentExcelRepository;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final InformatiiFacultateRepository informatiiFacultateRepository;
    private final SecretaraRepository secretaraRepository;
    private final FileService fileService;
    private final StudentExcelRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("admin not found " + username));
    }

    public InformatiiFacultate addInformatiiFacultate(InformatiiFacultateDto informatiiFacultateDto) {
        InformatiiFacultate informatiiFacultate = informatiiFacultateDto.toInformatiiFacultate();
        return informatiiFacultateRepository.save(informatiiFacultate);

    }

    public Secretara addSecretare(SecretareDto secretareDto) {
        Secretara secretara = secretareDto.toSecretara();
        return secretaraRepository.save(secretara);
    }

    public AddStudentiExcelResponse addStudentiExcel(MultipartFile file) throws IOException {
        studentRepository.deleteAll();
        InputStream inputStream = file.getInputStream();
        AddStudentiExcelResponse addStudentiExcelResponse = fileService.loadStudentsFromExcel(inputStream);
        studentRepository.saveAll(addStudentiExcelResponse.successStudents());
        fileService.saveStudentsExcelToLocal(file);
        return addStudentiExcelResponse;
    }

    public void reset() {
        //TODO not mock year, add from controller
        fileService.generateYearReport("2023-2024");
    }
}
