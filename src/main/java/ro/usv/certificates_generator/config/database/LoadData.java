package ro.usv.certificates_generator.config.database;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.usv.certificates_generator.model.StudentExcel;
import ro.usv.certificates_generator.repository.StudentExcelRepository;
import ro.usv.certificates_generator.service.FileService;

import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LoadData implements CommandLineRunner {

    private final FileService fileService;
    private final StudentExcelRepository repository;

    @Override
    public void run(String... args) throws Exception {
        String filePath = "studenti.xlsx";
        URL resourceUrl = getClass().getClassLoader().getResource(filePath);
        if (resourceUrl == null) {
            System.out.println("File " + filePath + " does not exist in the resources folder.");
            return;
        }
        List<StudentExcel> students = fileService.loadStudentsFromExcel(filePath).successStudents();
        repository.saveAll(students);

    }
}
