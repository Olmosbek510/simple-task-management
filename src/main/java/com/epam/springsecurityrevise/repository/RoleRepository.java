package com.epam.springsecurityrevise.repository;

import com.epam.springsecurityrevise.model.Role;
import com.epam.springsecurityrevise.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsRoleByName(RoleName value);

    Optional<Role> findRoleByName(RoleName roleName);
}