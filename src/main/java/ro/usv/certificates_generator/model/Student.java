package ro.usv.certificates_generator.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    String email;
    String programStudiu;
    String cicluStudiu;
    String domeniuStudiu;
    String anStudiu;
    String formaInvatamant;
    String finantare;
    String numeComplet;
    char sex;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    List<AdverintaStudent> adeverinte;

    public Student(String email, String programStudiu, String cicluStudiu, String domeniuStudiu, String anStudiu, String formaInvatamant, String finantare, String numeComplet, char sex) {
        this.email = email;
        this.programStudiu = programStudiu;
        this.cicluStudiu = cicluStudiu;
        this.domeniuStudiu = domeniuStudiu;
        this.anStudiu = anStudiu;
        this.formaInvatamant = formaInvatamant;
        this.finantare = finantare;
        this.numeComplet = numeComplet;
        this.sex = sex;
    }
}
