package sas.service.services.impl;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sas.data.models.User;
import sas.data.repositories.UserRepository;
import sas.service.models.UserServiceModel;
import sas.service.services.HashingService;
import sas.service.services.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final HashingService hashingService;


    public UserServiceImpl(ModelMapper mapper, UserRepository userRepository, HashingService hashingService){
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    @Override
    public UserServiceModel getByUsername(String username) {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        return this.mapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel getByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        if(user.isEmpty()){
            return null;
        }
        return this.mapper.map(user,UserServiceModel.class);
    }

    @Override
    public void updateUser(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(this.hashingService.hash(userServiceModel.getPassword()));
        User user = this.userRepository.findByUsername(userServiceModel.getUsername()).get();
        user.setPassword(userServiceModel.getPassword());
        user.setEmail(userServiceModel.getEmail());
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElse(null);

        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
