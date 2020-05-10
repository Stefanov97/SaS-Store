package sas.service.services.impl;

import org.springframework.stereotype.Service;
import sas.data.models.Role;
import sas.data.repositories.RoleRepository;
import sas.service.services.RoleService;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRoles() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
        }
    }

    @Override
    public List<Role> getAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role getByAuth(String auth) {
        return this.roleRepository.findByAuthority(auth);
    }
}
