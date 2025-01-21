package com.epam.springsecurityrevise.repository;

import com.epam.springsecurityrevise.model.TokenType;
import com.epam.springsecurityrevise.model.enums.TokenTypeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenTypeRepository extends JpaRepository<TokenType, Long> {
    boolean existsTokenTypeByTokenTypeName(TokenTypeName name);

    Optional<TokenType> findTokenTypeByTokenTypeName(TokenTypeName tokenTypeName);
}