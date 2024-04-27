package ro.usv.certificates_generator.model;

import jakarta.persistence.Entity;
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
public class AdverintaStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int numarInregistrare;
    private int numarOrdine;
    private LocalDate dataInregistrarii;
    private String scopAdeverinta;
    private String anUniversitar;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public String nrInregistrare() {
        String formattedDate = dataInregistrarii.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).replace(".", "");
        return numarInregistrare + ".A." + numarOrdine + "/" + formattedDate;
    }

}
