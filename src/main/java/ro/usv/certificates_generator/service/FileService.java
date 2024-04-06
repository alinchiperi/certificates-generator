package ro.usv.certificates_generator.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.certificates_generator.dto.AddStudentiExcelResponse;
import ro.usv.certificates_generator.model.AdverintaRaportStudent;
import ro.usv.certificates_generator.model.StudentExcel;
import ro.usv.certificates_generator.repository.AdverintaRaportStudentRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileService {
    private final AdverintaRaportStudentRepository adverintaRaportStudentRepository;
    private final List<StudentExcel> failsStudents = new ArrayList<>();

    public FileService(AdverintaRaportStudentRepository adverintaRaportStudentRepository) {
        this.adverintaRaportStudentRepository = adverintaRaportStudentRepository;
    }


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

    public void generateYearReport(String year) {

        List<AdverintaRaportStudent> adeverinte = adverintaRaportStudentRepository.findByAnStudiu(year);
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(year);
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Nr. înregistrare", "Data înregistrării", "Nume student", "Inițiala tatălui", "Prenume student",
                    "Domeniu de studii", "Program de studiu", "Forma de învățământ", "Tipul studiilor universitare",
                    "An de studiu", "Finanțare student", "Scop adeverință"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            int rowNum = 1;
            for (AdverintaRaportStudent adverinta : adeverinte) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(adverinta.getNumarInregistrare());
                row.createCell(1).setCellValue(adverinta.getDataInregistrarii().toString());
                row.createCell(2).setCellValue(adverinta.getNumeStudent());
                row.createCell(3).setCellValue(adverinta.getInitialaTatalui());
                row.createCell(4).setCellValue(adverinta.getPrenumeStudent());
                row.createCell(5).setCellValue(adverinta.getDomeniuStudii());
                row.createCell(6).setCellValue(adverinta.getProgramStudiu());
                row.createCell(7).setCellValue(adverinta.getFormaInvatamant());
                row.createCell(8).setCellValue(adverinta.getTipStudii());
                row.createCell(9).setCellValue(adverinta.getAnStudiu());
                row.createCell(10).setCellValue(adverinta.getFinantareStudent());
                row.createCell(11).setCellValue(adverinta.getScopAdeverinta());
            }

            try (FileOutputStream fileOut = new FileOutputStream(year+".xlsx")) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
