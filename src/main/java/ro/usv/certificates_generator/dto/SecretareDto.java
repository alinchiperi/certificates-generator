package ro.usv.certificates_generator.dto;

import ro.usv.certificates_generator.model.Secretara;

public record SecretareDto(
        String nume,
        String prenume,
        String titlu,
        String email
) {
    public Secretara toSecretara() {
        return new Secretara(nume, prenume, titlu, email);
    }
}
