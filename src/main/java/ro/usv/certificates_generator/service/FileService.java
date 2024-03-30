package ro.usv.certificates_generator.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.certificates_generator.dto.AddStudentiExcelResponse;
import ro.usv.certificates_generator.model.StudentExcel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileService {

    private List<StudentExcel> failsStudents = new ArrayList<>();


    public AddStudentiExcelResponse loadStudentsFromExcel(String filePath) throws IOException {
        List<StudentExcel> successStudents = new ArrayList<>();

        InputStream inputStream = new ClassPathResource(filePath).getInputStream();

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        // Skip header row if needed
        if (rowIterator.hasNext()) {
            rowIterator.next(); // Skip header row
        }
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            StudentExcel studentExcel = createStudentFromRow(row);


            try {
                validateEmailDomain(studentExcel.getEmail());

                successStudents.add(studentExcel);


            } catch (IllegalArgumentException e) {
                failsStudents.add(studentExcel);
            }
        }
        workbook.close();
        return new AddStudentiExcelResponse(successStudents, failsStudents);
    }

    public AddStudentiExcelResponse saveStudentsExcelToLocal(MultipartFile file) throws IOException {

        String fileName = "studenti.xlsx";

        File directory = new File("src\\main\\resources\\");

        File newFile = new File(directory.getAbsolutePath() + File.separator + fileName);
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            fos.write(file.getBytes());
        }

        return loadStudentsFromExcel("studenti.xlsx");
    }

    private void validateEmailDomain(String email) throws IllegalArgumentException {
        if (!email.endsWith("@student.usv.ro")) {
            throw new IllegalArgumentException("Adresa de email nu aparține domeniului @student.usv.ro.");
        }
    }

    private String getStringCellValue(Cell cell) {
        return cell != null ? cell.getStringCellValue().trim() : "";
    }

    private StudentExcel createStudentFromRow(Row row) {
        String email = getStringCellValue(row.getCell(0));
        String programStudiu = getStringCellValue(row.getCell(1));
        String cicluStudiu = getStringCellValue(row.getCell(2));
        String anStudiu = getStringCellValue(row.getCell(3));
        String domeniuStudiu = getStringCellValue(row.getCell(4));
        String formaInvatamant = getStringCellValue(row.getCell(5));
        String finantare = getStringCellValue(row.getCell(6));
        String initialaTata = getStringCellValue(row.getCell(7));
        char sex = getStringCellValue(row.getCell(8)).charAt(0);

        return new StudentExcel(email, programStudiu, cicluStudiu, domeniuStudiu, anStudiu, formaInvatamant, finantare, initialaTata, sex);
    }
}
