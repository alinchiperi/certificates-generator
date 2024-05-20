package ro.usv.certificates_generator.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true)
    private String email;


    @OneToMany(mappedBy = "secretara", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AdeverintaRespinsa> adeverintaRespinsa = new ArrayList<>();


    public Secretara(String nume, String prenume, String titlu, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.titlu = titlu;
        this.email = email;
    }

    public String getNumeComplet() {
      return titlu + " " + prenume + " " + nume.toUpperCase();
    }
}
