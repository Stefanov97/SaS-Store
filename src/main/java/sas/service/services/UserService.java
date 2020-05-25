package sas.service.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import sas.service.models.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserServiceModel> getAll();

    UserServiceModel getByUsername(String username);

    UserServiceModel getByEmail(String email);

    void updatePassword(UserServiceModel userServiceModel);

    void updateEmail(UserServiceModel userServiceModel);

    void demoteAdmin(String id);

    void promoteAdmin(String id);
}
