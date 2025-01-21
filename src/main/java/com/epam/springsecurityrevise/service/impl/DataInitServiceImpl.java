package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.service.DataInitService;
import com.epam.springsecurityrevise.service.RoleService;
import com.epam.springsecurityrevise.service.TokenTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataInitServiceImpl implements DataInitService {
    private final RoleService roleService;
    private final TokenTypeService tokenTypeService;

    @Override
    public void initConstants() {
        roleService.initRoles();
        tokenTypeService.initTokenTypes();
    }
}
