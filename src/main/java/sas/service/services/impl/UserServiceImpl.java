package sas.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sas.data.models.User;
import sas.data.repositories.RoleRepository;
import sas.data.repositories.UserRepository;
import sas.service.models.UserServiceModel;
import sas.service.services.HashingService;
import sas.service.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final RoleRepository roleRepository;


    public UserServiceImpl(ModelMapper mapper, UserRepository userRepository, HashingService hashingService, RoleRepository roleRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserServiceModel> getAll() {
        return this.userRepository.findAll().stream().map(u -> this.mapper.map(u, UserServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public UserServiceModel getByUsername(String username) {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel getByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return null;
        }
        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public void updatePassword(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(this.hashingService.hash(userServiceModel.getPassword()));
        Optional<User> optionalUser = this.userRepository.findByUsername(userServiceModel.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(userServiceModel.getPassword());
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public void updateEmail(UserServiceModel userServiceModel) {
        Optional<User> optionalUser = this.userRepository.findByUsername(userServiceModel.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userServiceModel.getEmail());
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public void demoteAdmin(String id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getAuthorities().removeIf(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public void promoteAdmin(String id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.addAuthority(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            this.userRepository.saveAndFlush(user);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElse(null);

        assert user != null;
        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
