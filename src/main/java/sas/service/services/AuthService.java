package sas.service.services;


import org.springframework.security.core.Authentication;
import sas.service.models.UserServiceModel;

public interface AuthService {
    void register(UserServiceModel user) ;

    boolean checkIfLogged(Authentication auth);
}
