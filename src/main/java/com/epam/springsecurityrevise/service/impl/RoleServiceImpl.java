package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.model.Role;
import com.epam.springsecurityrevise.model.enums.RoleName;
import com.epam.springsecurityrevise.repository.RoleRepository;
import com.epam.springsecurityrevise.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void initRoles() {
        Arrays.stream(RoleName.values())
                .filter(value -> !roleRepository.existsRoleByName(value))
                .map(value -> Role.builder().name(value).build())
                .forEach(roleRepository::save);
    }

    @Override
    public Role findByName(RoleName roleName) throws RoleNotFoundException {
        return roleRepository.findRoleByName(roleName).orElseThrow(() ->
                new RoleNotFoundException("Role '%s' not found".formatted(roleName.name()))
        );
    }
}
