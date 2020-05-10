package sas.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import sas.base.BaseTest;
import sas.data.models.User;
import sas.data.repositories.UserRepository;
import sas.service.models.UserServiceModel;
import sas.service.services.HashingService;
import sas.service.services.UserService;

public class UserServiceTest extends BaseTest {
    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;
    @Autowired
    HashingService hashingService;
    @MockBean
    UserRepository userRepository;


    @Test
    void updateUserShouldUpdatePasswordCorrectly() {
        User myUser = new User();
        myUser.setUsername("MyUser");
        myUser.setPassword("123");
        myUser.setEmail("myTestedEmail@abv.bg");

        Mockito.when(this.userRepository.findByUsername("MyUser")).thenReturn(java.util.Optional.of(myUser));
        UserServiceModel model = new UserServiceModel();
        model.setUsername("MyUser");
        model.setPassword("newPassword");
        model.setEmail("myTestedEmail@abv.bg");
        String newPassword = model.getPassword();
        this.userService.updateUser(model);
        boolean matches = this.hashingService.matches(newPassword,myUser.getPassword());
        Assert.assertTrue(matches);
    }

    @Test
    void updateUserShouldUpdateEmailCorrectly() {
        User myUser = new User();
        myUser.setUsername("MyUser");
        myUser.setPassword("123");
        myUser.setEmail("myTestedEmail@abv.bg");

        Mockito.when(this.userRepository.findByUsername("MyUser")).thenReturn(java.util.Optional.of(myUser));
        UserServiceModel model = new UserServiceModel();
        model.setUsername("MyUser");
        model.setPassword("newPassword");
        model.setEmail("myChangedEmail@abv.bg");
        String newEmail = model.getEmail();
        this.userService.updateUser(model);
        boolean matches = myUser.getEmail().equals(newEmail);
        Assert.assertTrue(matches);
    }
}
