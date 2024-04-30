package ro.usv.certificates_generator.service.manager;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class NumarOrdineManager {
    private final AtomicInteger count = new AtomicInteger(0);

    public int getNumarAdeverinta() {
        return count.incrementAndGet();
    }
}
