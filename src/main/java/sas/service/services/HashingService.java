package sas.service.services;

public interface HashingService {
    String hash(String str);
    boolean matches(String notEncrypted, String encrypted);
}
