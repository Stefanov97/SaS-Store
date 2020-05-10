package sas.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sas.service.services.UserService;
import sas.validation.annotations.UniqueEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<UniqueEmail,String> {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return this.userService.getByEmail(email) == null;
    }
}
