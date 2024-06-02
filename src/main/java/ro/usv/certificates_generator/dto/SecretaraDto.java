package ro.usv.certificates_generator.dto;

import ro.usv.certificates_generator.model.Secretara;

public record SecretaraDto(
        String nume,
        String prenume,
        String titlu,
        String email
) {
    public Secretara toSecretara() {
        return new Secretara(nume, prenume, titlu, email);
    }
    public static SecretaraDto fromSecretara(Secretara secretara) {
        return new SecretaraDto(secretara.getNume(), secretara.getPrenume(), secretara.getTitlu(), secretara.getEmail());
    }
}
