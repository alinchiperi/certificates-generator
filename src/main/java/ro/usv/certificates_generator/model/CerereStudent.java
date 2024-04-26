package ro.usv.certificates_generator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class CerereStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String emailStudent;
    private String numeStudent;
    private String prenumeStudent;
    private LocalDate dataCerere;
    private String scop;

    @Enumerated(EnumType.STRING)
    private CerereStatus status = CerereStatus.PENDING;

    public CerereStudent(String emailStudent, String numeStudent, String prenumeStudent, LocalDate dataCerere, String scop) {
        this.emailStudent = emailStudent;
        this.numeStudent = numeStudent;
        this.prenumeStudent = prenumeStudent;
        this.dataCerere = dataCerere;
        this.scop = scop;
    }


}
