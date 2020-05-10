package sas.service.services;

import sas.data.models.Role;

import java.util.List;

public interface RoleService {
    void seedRoles();

    List<Role> getAll();

    Role getByAuth(String auth);
}
