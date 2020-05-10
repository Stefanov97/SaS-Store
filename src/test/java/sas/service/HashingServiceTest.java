package sas.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sas.base.BaseTest;
import sas.service.services.HashingService;

public class HashingServiceTest extends BaseTest {
    @Autowired
    HashingService hashingService;

    @Test
    void hashShouldEncryptTheUserPassword() {
        String password = "myTestPassword";
        String hashedPassword = this.hashingService.hash(password);
        Assert.assertNotEquals(password,hashedPassword);
    }

    @Test
    void matchesShouldReturnTrueIfPasswordsAreMatching() {
        String password = "myTestPassword";
        String encrypted = this.hashingService.hash(password);
        boolean matches = this.hashingService.matches(password,encrypted);
        Assert.assertTrue(matches);
    }

    @Test
    void matchesShouldReturnFalseIfPasswordsNotMatching() {
        String password = "myTestPassword";
        String encrypted = this.hashingService.hash(password);
        String otherPassword = "otherPassword";
        boolean matches = this.hashingService.matches(otherPassword,encrypted);
        Assert.assertFalse(matches);
    }

}
