package sas.service.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import sas.service.models.UserServiceModel;

public interface UserService extends UserDetailsService {
    UserServiceModel getByUsername(String username);

    UserServiceModel getByEmail(String email);

    void updateUser(UserServiceModel userServiceModel);
}
