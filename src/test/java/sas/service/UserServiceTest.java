package sas.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import sas.base.BaseTest;
import sas.data.models.Role;
import sas.data.models.User;
import sas.data.repositories.UserRepository;
import sas.service.models.UserServiceModel;
import sas.service.services.HashingService;
import sas.service.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest extends BaseTest {
    private final static String BASE_USERNAME = "MyUser";
    private final static String BASE_PASSWORD = "123";
    private final static String OTHER_BASE_PASSWORD = "1234";
    private final static String BASE_EMAIL = "myemail@abv.bg";
    private final static String OTHER_BASE_EMAIL = "otheremail@gmail.com";
    private final static String ADMIN_AUTHORITY = "ROLE_ADMIN";
    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;
    @Autowired
    HashingService hashingService;
    @MockBean
    UserRepository userRepository;


    @Test
    void updatePasswordShouldUpdatePasswordCorrectly() {
        User myUser = new User();
        myUser.setUsername(BASE_USERNAME);
        myUser.setPassword(BASE_PASSWORD);

        Mockito.when(this.userRepository.findByUsername(BASE_USERNAME)).thenReturn(java.util.Optional.of(myUser));
        UserServiceModel model = new UserServiceModel();
        model.setUsername(BASE_USERNAME);
        model.setPassword(OTHER_BASE_PASSWORD);
        String newPassword = model.getPassword();
        this.userService.updatePassword(model);
        boolean matches = this.hashingService.matches(newPassword,myUser.getPassword());
        Assert.assertTrue(matches);
    }

    @Test
    void updateEmailShouldUpdateEmailCorrectly() {
        User myUser = new User();
        myUser.setUsername(BASE_USERNAME);
        myUser.setEmail(BASE_EMAIL);

        Mockito.when(this.userRepository.findByUsername(BASE_USERNAME)).thenReturn(java.util.Optional.of(myUser));
        UserServiceModel model = new UserServiceModel();
        model.setUsername(BASE_USERNAME);
        model.setEmail(OTHER_BASE_EMAIL);
        String newEmail = model.getEmail();
        this.userService.updateEmail(model);
        boolean matches = myUser.getEmail().equals(newEmail);
        Assert.assertTrue(matches);
    }

    @Test
    void demoteAdminShouldRemoveAdminRoleFromUserAuthorities() {
        User myUser = new User();
        myUser.setId(BASE_USERNAME);
        List<Role> myRoles = new ArrayList<>();
        myRoles.add(new Role(ADMIN_AUTHORITY));
        myUser.setAuthorities(myRoles);
        Mockito.when(this.userRepository.findById(BASE_USERNAME)).thenReturn(java.util.Optional.of(myUser));
        this.userService.demoteAdmin(myUser.getId());
        boolean isAdminRoleRemoved = myUser.getAuthorities().isEmpty();
        Assert.assertTrue(isAdminRoleRemoved);
    }

    @Test
    void promoteAdminShouldAddAdminRoleToUserAuthorities() {
        User myUser = new User();
        myUser.setId(BASE_USERNAME);
        List<Role> myRoles = new ArrayList<>();
        myUser.setAuthorities(myRoles);
        Mockito.when(this.userRepository.findById(BASE_USERNAME)).thenReturn(java.util.Optional.of(myUser));
        this.userService.promoteAdmin(myUser.getId());
        boolean isAdminRoleAdded = !myUser.getAuthorities().isEmpty();
        Assert.assertTrue(isAdminRoleAdded);
    }
}
