package sas.validation.validators;
import sas.validation.annotations.PasswordsMatch;
import sas.web.models.UserEditPasswordModel;
import sas.web.models.UserRegisterModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsValidator implements ConstraintValidator<PasswordsMatch,Object> {
    private String firstFieldName;
    private String message;
    @Override
    public boolean isValid(Object userModel, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        if(userModel instanceof UserRegisterModel){
            UserRegisterModel user = (UserRegisterModel) userModel;

            valid =  user.getPassword().equals(user.getConfirmPassword());
        }else {
            UserEditPasswordModel user = (UserEditPasswordModel) userModel;

            valid =  user.getPassword().equals(user.getConfirmPassword());
        }
        if (!valid){
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        message = constraintAnnotation.message();
    }
}
