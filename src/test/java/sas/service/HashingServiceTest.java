package sas.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sas.base.BaseTest;
import sas.service.services.HashingService;

public class HashingServiceTest extends BaseTest {
    private final static String TEST_PASSWORD = "myTestPassword";
    private final static String OTHER_TEST_PASSWORD = "myTestPassword2";
    @Autowired
    HashingService hashingService;

    @Test
    void hashShouldEncryptTheUserPassword() {
        String hashedPassword = this.hashingService.hash(TEST_PASSWORD);
        Assert.assertNotEquals(TEST_PASSWORD,hashedPassword);
    }

    @Test
    void matchesShouldReturnTrueIfPasswordsAreMatching() {
        String encrypted = this.hashingService.hash(TEST_PASSWORD);
        boolean matches = this.hashingService.matches(TEST_PASSWORD,encrypted);
        Assert.assertTrue(matches);
    }

    @Test
    void matchesShouldReturnFalseIfPasswordsNotMatching() {
        String encrypted = this.hashingService.hash(TEST_PASSWORD);

        boolean matches = this.hashingService.matches(OTHER_TEST_PASSWORD,encrypted);
        Assert.assertFalse(matches);
    }

}
