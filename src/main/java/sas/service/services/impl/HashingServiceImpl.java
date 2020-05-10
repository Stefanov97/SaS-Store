package sas.service.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sas.service.services.HashingService;

@Service
public class HashingServiceImpl implements HashingService {
    private final PasswordEncoder passwordEncoder;

    public HashingServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public String hash(String str) {
        return passwordEncoder.encode(str);
    }

    @Override
    public boolean matches(String notEncrypted, String encrypted) {
        return passwordEncoder.matches(notEncrypted,encrypted);
    }
}
