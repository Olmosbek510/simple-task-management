package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.model.TokenType;
import com.epam.springsecurityrevise.model.enums.TokenTypeName;
import com.epam.springsecurityrevise.repository.TokenTypeRepository;
import com.epam.springsecurityrevise.service.TokenTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class TokenTypeServiceImpl implements TokenTypeService {
    private final TokenTypeRepository tokenTypeRepository;

    @Override
    public void initTokenTypes() {
        Arrays.stream(TokenTypeName.values())
                .filter(name -> !tokenTypeRepository.existsTokenTypeByTokenTypeName(name))
                .map(name -> TokenType.builder().tokenTypeName(name).build())
                .forEach(tokenTypeRepository::save);
    }

    @Override
    public TokenType findByName(TokenTypeName tokenTypeName) throws TokenTypeNotFoundException {
        return tokenTypeRepository.findTokenTypeByTokenTypeName(tokenTypeName).orElseThrow(() ->
                new TokenTypeNotFoundException("Token type '%s' not found".formatted(tokenTypeName)));
    }
}
