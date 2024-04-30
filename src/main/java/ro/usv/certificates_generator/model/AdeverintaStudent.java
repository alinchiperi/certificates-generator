package ro.usv.certificates_generator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdeverintaStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int numarInregistrare;
    private int numarOrdine;

    private LocalDate dataInregistrarii;
    private LocalDate dataCerere;

    private String scop;
    private String anUniversitar;

    @Enumerated(EnumType.STRING)
    private CerereStatus status = CerereStatus.PENDING;

    private boolean isPrinted = false;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public AdeverintaStudent(Student student, String scop, LocalDate dataCerere) {
        this.student = student;
        this.scop = scop;
        this.dataCerere = dataCerere;
    }


    public String getNrInregistrare() {
        String formattedDate = dataInregistrarii.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return numarInregistrare + ".A." + numarOrdine + "/" + formattedDate;
    }

}
