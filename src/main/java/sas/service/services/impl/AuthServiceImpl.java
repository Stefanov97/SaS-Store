package sas.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sas.data.models.User;
import sas.data.repositories.RoleRepository;
import sas.data.repositories.UserRepository;
import sas.service.models.UserServiceModel;
import sas.service.services.AuthService;
import sas.service.services.HashingService;
import sas.service.services.RoleService;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final HashingService hashingService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    @Autowired
    public AuthServiceImpl(UserRepository userRepository, ModelMapper mapper, HashingService hashingService, RoleService roleService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.hashingService = hashingService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(UserServiceModel userServiceModel){
        this.roleService.seedRoles();
        userServiceModel.setPassword(this.hashingService.hash(userServiceModel.getPassword()));
        User user = this.mapper.map(userServiceModel,User.class);
        if(userRepository.count() == 0){
            user.setAuthorities(this.roleRepository.findAll());
        }else {
            user.setAuthorities(List.of(this.roleRepository.findByAuthority("ROLE_USER")));
        }
        this.userRepository.saveAndFlush(user);

    }

    @Override
    public boolean checkIfLogged(Authentication auth) {
        return (!(auth instanceof AnonymousAuthenticationToken));
    }
}
