package ro.usv.certificates_generator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class AdeverintaRespinsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String motiv;
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "secretara_id") // name of the foreign key column in AdeverintaRespinsa table
    private Secretara secretara;

    @OneToOne
    @JoinColumn(name = "adeverinta_student_id") // name of the foreign key column in AdeverintaRespinsa table
    private AdeverintaStudent adeverintaStudent;
}
