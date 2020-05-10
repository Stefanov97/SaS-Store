package sas.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sas.validation.annotations.PasswordsMatch;
import sas.validation.annotations.UniqueEmail;
import sas.validation.annotations.UniqueUsername;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@PasswordsMatch(first = "password", second = "confirmPassword", message = "Passwords does not match")
public class UserRegisterModel {
    @Size(min = 3, max = 15, message = "Username should be between 3 and 15 symbols")
    @UniqueUsername
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @NotEmpty(message = "Confirm password cannot be empty")
    private String confirmPassword;
    @Pattern(regexp = "^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$", message = "Please enter a valid email")
    @UniqueEmail
    private String email;


}
