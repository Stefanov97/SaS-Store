package sas.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sas.service.models.UserServiceModel;
import sas.service.services.HashingService;
import sas.service.services.UserService;
import sas.validation.annotations.OldPasswordIsCorrect;
import sas.web.models.UserEditProfileModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OldPasswordValidator implements ConstraintValidator<OldPasswordIsCorrect, Object> {
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

        UserEditProfileModel user = (UserEditProfileModel) userModel;
        UserServiceModel userServiceModel = this.userService.getByUsername(user.getUsername());
        valid = this.hashingService.matches(user.getOldPassword(), userServiceModel.getPassword());

        if (!valid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }

    @Override
    public void initialize(OldPasswordIsCorrect constraintAnnotation) {
        fieldName = constraintAnnotation.field();
        message = constraintAnnotation.message();
    }
}
