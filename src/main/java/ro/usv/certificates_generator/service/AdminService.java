package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.certificates_generator.dto.AddStudentiExcelResponse;
import ro.usv.certificates_generator.dto.InformatiiFacultateDto;
import ro.usv.certificates_generator.dto.SecretaraDto;
import ro.usv.certificates_generator.dto.StudentDto;
import ro.usv.certificates_generator.model.InformatiiFacultate;
import ro.usv.certificates_generator.model.Secretara;
import ro.usv.certificates_generator.model.Student;
import ro.usv.certificates_generator.repository.AdminRepository;
import ro.usv.certificates_generator.repository.InformatiiFacultateRepository;
import ro.usv.certificates_generator.repository.SecretaraRepository;
import ro.usv.certificates_generator.repository.StudentExcelRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final InformatiiFacultateRepository informatiiFacultateRepository;
    private final InformatiiFacultateService informatiiFacultateService;
    private final SecretaraRepository secretaraRepository;
    private final FileService fileService;
    private final StudentExcelRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username)
                .map(admin ->
                    User.withUsername(admin.getUsername())
                            .password(admin.getPassword())
                            .authorities("ROLE_ADMIN")
                            .build())
                .orElseThrow(() -> new UsernameNotFoundException("admin not found " + username));

    }

    public InformatiiFacultate addInformatiiFacultate(InformatiiFacultateDto informatiiFacultateDto) {
        return informatiiFacultateService.addInformatiiFacultate(informatiiFacultateDto);

    }

    public Secretara addSecretare(SecretaraDto secretaraDto) {
        Secretara secretara = secretaraDto.toSecretara();
        return secretaraRepository.save(secretara);
    }

    public AddStudentiExcelResponse addStudentiExcel(MultipartFile file) throws IOException {
        studentRepository.deleteAll();
        InputStream inputStream = file.getInputStream();
        AddStudentiExcelResponse addStudentiExcelResponse = fileService.loadStudentsFromExcel(inputStream);
        studentRepository.saveAll(addStudentiExcelResponse.successStudents());
//        fileService.saveStudentsExcelToLocal(file);
        return addStudentiExcelResponse;
    }

    public byte[] reset() {
        //TODO not mock year, add from controller
//        String year = informatiiFacultateService.getInformatiiFacultate().getAnUniversitar();
        String year = "2023-2024";
        byte[] bytes = fileService.generateYearReport(year);
        studentRepository.deleteAll();
        return bytes;

    }

    public InformatiiFacultateDto updateNumeFacultate(String numeFacultate) {
        InformatiiFacultate informatiiFacultate = getInformatiiFacultate();
        informatiiFacultate.setNumeFacultate(numeFacultate);
        informatiiFacultateRepository.save(informatiiFacultate);
        return InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate);
    }

    public InformatiiFacultate getInformatiiFacultate() {
       return informatiiFacultateRepository.getInformatiiFacultate();
    }

    public InformatiiFacultateDto updateNumeDecan(String numeDecan) {
        InformatiiFacultate informatiiFacultate = getInformatiiFacultate();
        informatiiFacultate.setNumeDecan(numeDecan);
        informatiiFacultateRepository.save(informatiiFacultate);
        return InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate);

    }

    public InformatiiFacultateDto updateNumeSecretaraSef(String numeSeretaraSef) {
        InformatiiFacultate informatiiFacultate = getInformatiiFacultate();
        informatiiFacultate.setNumeSecretarSef(numeSeretaraSef);
        informatiiFacultateRepository.save(informatiiFacultate);
        return InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate);
    }

    public InformatiiFacultateDto updatePrescurtareFacultate(String prescurtareFacultate) {
        InformatiiFacultate informatiiFacultate = getInformatiiFacultate();
        informatiiFacultate.setPrecurtareFacultate(prescurtareFacultate);
        informatiiFacultateRepository.save(informatiiFacultate);
        return InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate);
    }

    public InformatiiFacultateDto updateAnUniversitar(String anUniversitar) {
        InformatiiFacultate informatiiFacultate = getInformatiiFacultate();
        informatiiFacultate.setAnUniversitar(anUniversitar);
        informatiiFacultateRepository.save(informatiiFacultate);
        return InformatiiFacultateDto.fromInformatiiFacultate(informatiiFacultate);
    }

    public List<SecretaraDto> getSecretare() {
        List<Secretara> allSecretary = secretaraRepository.findAll();


        return allSecretary.stream().map(SecretaraDto::fromSecretara).collect(Collectors.toList());
    }

    public List<StudentDto> getStudents() {
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream().map(StudentDto::fromStudent).collect(Collectors.toList());
    }

    public void deleteSecretara(int id) {
        secretaraRepository.deleteById(id);
    }

    public void deleteStudent(String email) {
        studentRepository.deleteById(email);
    }

    public Secretara updateSecretara( int id, SecretaraDto secretaraDto) {
        Secretara secretara = secretaraRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Secretara with id " + id + " not found"));

        secretara.setNume(secretaraDto.nume());
        secretara.setPrenume(secretaraDto.prenume());
        secretara.setTitlu(secretaraDto.titlu());
        secretara.setEmail(secretaraDto.email());
        secretaraRepository.save(secretara);
        return secretara;

    }
}
