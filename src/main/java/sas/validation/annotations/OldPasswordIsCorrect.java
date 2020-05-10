package sas.validation.annotations;

import sas.validation.validators.OldPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OldPasswordValidator.class)
@Documented
public @interface OldPasswordIsCorrect {
    String message() default "Password is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();
}
