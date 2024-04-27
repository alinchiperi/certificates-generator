package ro.usv.certificates_generator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class AdverintaRaportStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int numarInregistrare;
    private int numarOrdine;
    private LocalDate dataInregistrarii;
    private String numeStudent;
    private char initialaTatalui;
    private String prenumeStudent;
    private String domeniuStudii;
    private String programStudiu;
    private String formaInvatamant;
    private String tipStudii;
    private String anStudiu;
    private String finantareStudent;
    private String scopAdeverinta;

    public String nrInregistrare() {
        String formattedDate = dataInregistrarii.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).replace(".", "");
        return numarInregistrare + ".A." + numarOrdine + "/" + formattedDate;
    }

}
