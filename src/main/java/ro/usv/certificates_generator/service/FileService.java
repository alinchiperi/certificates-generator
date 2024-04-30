package ro.usv.certificates_generator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.usv.certificates_generator.dto.AddStudentiExcelResponse;
import ro.usv.certificates_generator.dto.AdeverintaAprobataDto;
import ro.usv.certificates_generator.model.AdeverintaStudent;
import ro.usv.certificates_generator.model.Student;
import ro.usv.certificates_generator.repository.AdeverintaStudentRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class FileService {
    private final AdeverintaStudentRepository adeverintaStudentRepository;
    private final List<Student> failsStudents = new ArrayList<>();
    List<Student> successStudents = new ArrayList<>();

    public FileService(AdeverintaStudentRepository adeverintaStudentRepository) {
        this.adeverintaStudentRepository = adeverintaStudentRepository;
    }

    public AddStudentiExcelResponse loadStudentsFromExcel(InputStream inputStream) {
        try {

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            // Skip header row if needed
            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip header row
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Student student = createStudentFromRow(row);

                try {
                    validateEmailDomain(student.getEmail());
                    successStudents.add(student);
                } catch (IllegalArgumentException e) {
                    failsStudents.add(student);
                }
            }

        } catch (IOException e) {
            log.error("Error while loading excel file", e);
        }
        return new AddStudentiExcelResponse(successStudents, failsStudents);
    }


    public void saveStudentsExcelToLocal(MultipartFile file) throws IOException {

        String fileName = "studenti.xlsx";

        File directory = new File("src\\main\\resources\\");

        File newFile = new File(directory.getAbsolutePath() + File.separator + fileName);
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            fos.write(file.getBytes());
        }

    }

    public void generateYearReport(String year) {

        List<AdeverintaStudent> adeverinte = adeverintaStudentRepository.findByAnUniversitar(year);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(year);
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Nr. înregistrare", "Data înregistrării", "Nume student",
                    "Domeniu de studii", "Program de studiu", "Forma de învățământ", "Tipul studiilor universitare",
                    "An de studiu", "Finanțare student", "Scop adeverință"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            int rowNum = 1;
            for (AdeverintaStudent adverinta : adeverinte) {
                Row row = sheet.createRow(rowNum++);
                Student student = adverinta.getStudent();

                row.createCell(0).setCellValue(adverinta.getNumarInregistrare());
                row.createCell(1).setCellValue(adverinta.getDataInregistrarii().toString());
                row.createCell(2).setCellValue(student.getNumeComplet());
                row.createCell(5).setCellValue(student.getDomeniuStudiu());
                row.createCell(6).setCellValue(student.getProgramStudiu());
                row.createCell(7).setCellValue(student.getFormaInvatamant());
                row.createCell(8).setCellValue(student.getCicluStudiu());
                row.createCell(9).setCellValue(student.getAnStudiu());
                row.createCell(10).setCellValue(student.getFinantare());

                row.createCell(11).setCellValue(adverinta.getScop());
            }

            try (FileOutputStream fileOut = new FileOutputStream(year + ".xlsx")) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateRaportForSecreatara(List<AdeverintaAprobataDto> adeverinte) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Adeverinte");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nr. înregistrare");
            headerRow.createCell(1).setCellValue("Data înregistrării");
            headerRow.createCell(2).setCellValue("Solicitant");
            headerRow.createCell(3).setCellValue("Adeverință(scop)");
            int rowNum = 1;
            for (AdeverintaAprobataDto adverinta : adeverinte) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(adverinta.numarInregistare());
                row.createCell(1).setCellValue(adverinta.dataCerere().toString());
                row.createCell(2).setCellValue(adverinta.numeStudent());
                row.createCell(3).setCellValue(adverinta.scop());
            }
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            return null;
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

    private Student createStudentFromRow(Row row) {
        String email = getStringCellValue(row.getCell(0));
        String programStudiu = getStringCellValue(row.getCell(1));
        String cicluStudiu = getStringCellValue(row.getCell(2));
        String anStudiu = getStringCellValue(row.getCell(3));
        String domeniuStudiu = getStringCellValue(row.getCell(4));
        String formaInvatamant = getStringCellValue(row.getCell(5));
        String finantare = getStringCellValue(row.getCell(6));
        String numeComplet = getStringCellValue(row.getCell(7));
        char sex = getStringCellValue(row.getCell(8)).charAt(0);

        return new Student(email, programStudiu, cicluStudiu, domeniuStudiu, anStudiu, formaInvatamant, finantare, numeComplet, sex);
    }


}
