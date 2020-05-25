package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sas.validation.annotations.PasswordIsCorrect;
import sas.validation.annotations.PasswordsMatch;


@Getter
@Setter
@NoArgsConstructor
@PasswordsMatch(first = "password", second = "confirmPassword", message = "Passwords does not match")
@PasswordIsCorrect(field ="oldPassword")
public class UserEditPasswordModel {
    private String username;
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
