package com.epam.springsecurityrevise.service;

import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.model.TokenType;
import com.epam.springsecurityrevise.model.enums.TokenTypeName;

public interface TokenTypeService {
    void initTokenTypes();

    TokenType findByName(TokenTypeName tokenTypeName) throws TokenTypeNotFoundException;
}
