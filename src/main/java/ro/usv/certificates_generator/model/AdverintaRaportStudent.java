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

    private String numarInregistrare;
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

}
