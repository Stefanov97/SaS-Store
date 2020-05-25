package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sas.validation.annotations.PasswordIsCorrect;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@PasswordIsCorrect(field ="password")
public class UserEditEmailModel {
    private String username;
    private String password;
    @Pattern(regexp = "^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$", message = "Please enter a valid email")
    private String email;
}
