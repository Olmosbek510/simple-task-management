package com.epam.springsecurityrevise.service;

import com.epam.springsecurityrevise.model.Role;
import com.epam.springsecurityrevise.model.enums.RoleName;

import javax.management.relation.RoleNotFoundException;

public interface RoleService {
    void initRoles();

    Role findByName(RoleName roleName) throws RoleNotFoundException;

}
