package ro.usv.certificates_generator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.repository.AdminRepository;

@Service
@RequiredArgsConstructor

public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("admin not found " + username));
    }
}
