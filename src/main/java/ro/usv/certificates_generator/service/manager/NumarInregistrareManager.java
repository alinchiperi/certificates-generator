package ro.usv.certificates_generator.service.manager;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NumarInregistrareManager {

    private ConcurrentHashMap<LocalDate, Integer> numarInregisrare = new ConcurrentHashMap<>();

    public int getNumarInregistrare(LocalDate  data) {
        return numarInregisrare.getOrDefault(data, 0);
    }

    public void setNumarInregisrare(LocalDate data, int numar) {
        numarInregisrare.put(data, numar);
    }
}
