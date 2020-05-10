package sas.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sas.data.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByAuthority(String auth);
}
