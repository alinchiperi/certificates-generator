package ro.usv.certificates_generator.dto;


public record AdeverintaPrintareDto(
        Integer id,
        String numarInregistrare,
        String student,
        String anStudiu,
        String programStudiu,
        String domeniuStudiu,
        String regim,
        String decan,
        String secretarSef,
        String scop,
        String secretara
) {


}
