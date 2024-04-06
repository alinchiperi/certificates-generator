package ro.usv.certificates_generator.config.database;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ro.usv.certificates_generator.model.StudentExcel;
import ro.usv.certificates_generator.repository.StudentExcelRepository;
import ro.usv.certificates_generator.service.FileService;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LoadData implements CommandLineRunner {

    private final FileService fileService;
    private final StudentExcelRepository repository;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("studenti.xlsx");
        if (!resource.exists()) {
            System.out.println("File studenti.xlsx does not exist in the resources folder.");
            return;
        }
        InputStream inputStream = resource.getInputStream();

        List<StudentExcel> students = fileService.loadStudentsFromExcel(inputStream).successStudents();
        repository.saveAll(students);

    }
}
