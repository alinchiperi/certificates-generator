package ro.usv.certificates_generator.config.database;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.usv.certificates_generator.model.StudentExcel;
import ro.usv.certificates_generator.repository.StudentExcelRepository;
import ro.usv.certificates_generator.service.FileService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class LoadData implements CommandLineRunner {

    private final FileService fileService;
    private final StudentExcelRepository repository;

    @Override
    public void run(String... args) throws Exception {
        List<StudentExcel> students = fileService.loadStudentsFromExcel("studenti.xlsx").successStudents();
        repository.saveAll(students);
    }
}
