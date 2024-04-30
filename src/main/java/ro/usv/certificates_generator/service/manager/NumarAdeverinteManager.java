package ro.usv.certificates_generator.service.manager;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NumarAdeverinteManager {

    private ConcurrentHashMap<LocalDate, Integer> numarAdeverinte = new ConcurrentHashMap<>();

    public int getNumarAdeverinte(LocalDate  data) {
        return numarAdeverinte.getOrDefault(data, 0);
    }

    public void setNumarAdeverinte(LocalDate data, int numar) {
        numarAdeverinte.put(data, numar);
    }
}
