package ro.usv.certificates_generator.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import ro.usv.certificates_generator.model.InformatiiFacultate;

public record InformatiiFacultateDto(
        int id,
        String numeFacultate,
        String precurtareFacultate,
        String anUniversitar,
        String numeDecan,
        String numeSecretarSef
) {

    public InformatiiFacultate toInformatiiFacultate() {
        return new InformatiiFacultate(this.id, this.numeFacultate, this.precurtareFacultate, this.anUniversitar, this.numeDecan, this.numeSecretarSef);
    }
}
