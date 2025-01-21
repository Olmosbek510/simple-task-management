package com.epam.springsecurityrevise.service;

import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    void saveToken(String token, UserDetails userDetails) throws UserNotFoundException, TokenTypeNotFoundException;

    void revokeAllTokensByUserId(Long userId);

    void revokeAllTokensByEmail(String email);

    boolean isRevoked(String token) ;

    void revokeToken(String jwt);
}
