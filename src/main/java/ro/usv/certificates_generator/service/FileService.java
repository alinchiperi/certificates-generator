package ro.usv.certificates_generator.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.model.StudentExcel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileService {

    public List<StudentExcel> loadStudentsFromExcel(String filePath) throws IOException {
        List<StudentExcel> studentExcels = new ArrayList<>();
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
            String email = row.getCell(0).getStringCellValue();
            String programStudiu = row.getCell(1).getStringCellValue();
            String cicluStudiu = row.getCell(2).getStringCellValue();
            String anStudiu = row.getCell(3).getStringCellValue();
            String domeniuStudiu = row.getCell(4).getStringCellValue();
            String formaInvatamant = row.getCell(5).getStringCellValue();
            String finantare = row.getCell(6).getStringCellValue();
            String initialaTata = row.getCell(7).getStringCellValue();
            char sex = row.getCell(8).getStringCellValue().charAt(0);
            StudentExcel studentExcel = new StudentExcel(email, programStudiu, cicluStudiu, domeniuStudiu, anStudiu, formaInvatamant, finantare, initialaTata, sex);
            studentExcels.add(studentExcel);


        }
        workbook.close();
        return studentExcels;


    }

}
