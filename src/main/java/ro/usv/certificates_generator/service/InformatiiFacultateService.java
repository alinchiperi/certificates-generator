package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.InformatiiFacultateDto;
import ro.usv.certificates_generator.model.InformatiiFacultate;
import ro.usv.certificates_generator.repository.InformatiiFacultateRepository;

@RequiredArgsConstructor
@Service
public class InformatiiFacultateService {
    private final InformatiiFacultateRepository informatiiFacultateRepository;

    public InformatiiFacultate addInformatiiFacultate(InformatiiFacultateDto informatiiFacultateDto) {
        InformatiiFacultate informatiiFacultate = informatiiFacultateDto.toInformatiiFacultate();
        return informatiiFacultateRepository.save(informatiiFacultate);

    }
    public InformatiiFacultate getInformatiiFacultate() {
        return informatiiFacultateRepository.getInformatiiFacultate();
    }

}
