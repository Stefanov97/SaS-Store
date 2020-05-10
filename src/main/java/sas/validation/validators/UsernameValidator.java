package sas.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sas.service.models.UserServiceModel;
import sas.service.services.UserService;
import sas.validation.annotations.UniqueUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UniqueUsername,String> {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return this.userService.getByUsername(username) == null;
    }
}
