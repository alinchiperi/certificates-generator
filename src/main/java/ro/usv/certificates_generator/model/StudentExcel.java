package ro.usv.certificates_generator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentExcel {
    @Id
    String email;
    String programStudiu;
    String cicluStudiu;
    String domeniuStudiu;
    String anStudiu;
    String formaInvatamant;
    String finantare;
    String initialaTata;
    char sex;

}
