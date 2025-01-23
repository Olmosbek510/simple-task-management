package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.exception.TokenNotFoundException;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.model.Token;
import com.epam.springsecurityrevise.model.TokenType;
import com.epam.springsecurityrevise.model.User;
import com.epam.springsecurityrevise.model.enums.TokenTypeName;
import com.epam.springsecurityrevise.repository.TokenRepository;
import com.epam.springsecurityrevise.service.TokenService;
import com.epam.springsecurityrevise.service.TokenTypeService;
import com.epam.springsecurityrevise.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    private final TokenTypeService tokenTypeService;
    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(String token, UserDetails userDetails) throws UserNotFoundException, TokenTypeNotFoundException {
        User user = userService.findByEmail(userDetails.getUsername());
        TokenType tokenType = tokenTypeService.findByName(TokenTypeName.BEARER);
        tokenRepository.save(Token.builder()
                .token(token)
                .tokenType(tokenType)
                .expired(false)
                .revoked(false)
                .user(user)
                .build());
    }

    @Override
    public void revokeAllTokensByUserId(Long userId) {
        Integer rowsModified = tokenRepository.revokeAllByUserId(userId);
    }

    @Override
    @Transactional
    public void revokeAllTokensByEmail(String email) {
        Integer revokedTokensCount = tokenRepository.revokeAllByEmail(email);
        log.info("{} tokens revoked", revokedTokensCount);
    }

    @Override
    public boolean isRevoked(String token) {
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        return optionalToken.map(Token::isRevoked).orElse(false);
    }

    @Override
    @Transactional
    public void revokeToken(String jwt) {
        Integer revokeToken = tokenRepository.revokeToken(jwt);
        log.info("Token revoked. Rows affected: {}", revokeToken);
    }
}
