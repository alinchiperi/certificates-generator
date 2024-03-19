package ro.usv.certificates_generator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdeverintaPrintareDto {
    private String student;
    private String anStudiu;
    private String programStudiu;
    private String domeniuStudiu;
    private String regim;
    private String decan;
    private String secretarSef;
    private String secreatara;
    private String scop;
}
