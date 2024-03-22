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
import org.springframework.stereotype.Service;

@Entity
@Table(name = "informatii_facultate")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class InformatiiFacultate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numeFacultate;
    private String precurtareFacultate;
    private String anUniversitar;
    private String numeDecan;
    private String numeSecretarSef;


}
