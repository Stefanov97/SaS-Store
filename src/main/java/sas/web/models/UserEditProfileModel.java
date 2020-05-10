package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sas.validation.annotations.OldPasswordIsCorrect;
import sas.validation.annotations.PasswordsMatch;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@PasswordsMatch(first = "password", second = "confirmPassword", message = "Passwords does not match")
@OldPasswordIsCorrect(field ="oldPassword")
public class UserEditProfileModel {
    private String username;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    @Pattern(regexp = "^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$", message = "Please enter a valid email")
    private String email;
}
