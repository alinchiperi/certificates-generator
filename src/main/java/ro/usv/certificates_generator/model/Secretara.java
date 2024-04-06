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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "secretara")
public class Secretara {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nume;
    private String prenume;
    private String titlu;
    private String email;


    public Secretara(String nume, String prenume, String titlu, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.titlu = titlu;
        this.email = email;
    }
}
