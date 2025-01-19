package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.service.DataInitService;
import com.epam.springsecurityrevise.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataInitServiceImpl implements DataInitService {
    private final RoleService roleService;

    @Override
    public void initConstants() {
        roleService.initRoles();
    }
}
