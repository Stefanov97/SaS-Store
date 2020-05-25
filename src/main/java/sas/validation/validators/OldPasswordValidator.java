package sas.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sas.service.models.UserServiceModel;
import sas.service.services.HashingService;
import sas.service.services.UserService;
import sas.validation.annotations.PasswordIsCorrect;
import sas.web.models.UserEditEmailModel;
import sas.web.models.UserEditPasswordModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OldPasswordValidator implements ConstraintValidator<PasswordIsCorrect, Object> {
    private String fieldName;
    private String message;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Autowired
    private HashingService hashingService;
    @Override
    public boolean isValid(Object userModel, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        if (userModel instanceof UserEditPasswordModel) {
            UserEditPasswordModel user = (UserEditPasswordModel) userModel;
            valid = validatePassword(user);
        } else if (userModel instanceof UserEditEmailModel) {
            UserEditEmailModel user = (UserEditEmailModel) userModel;
            valid = validatePassword(user);
        }

        if (!valid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }

    @Override
    public void initialize(PasswordIsCorrect constraintAnnotation) {
        fieldName = constraintAnnotation.field();
        message = constraintAnnotation.message();
    }

    private boolean validatePassword(UserEditPasswordModel user) {
        UserServiceModel userServiceModel = this.userService.getByUsername(user.getUsername());
        return this.hashingService.matches(user.getOldPassword(), userServiceModel.getPassword());
    }

    private boolean validatePassword(UserEditEmailModel user) {
        UserServiceModel userServiceModel = this.userService.getByUsername(user.getUsername());
        return this.hashingService.matches(user.getPassword(), userServiceModel.getPassword());
    }
}
