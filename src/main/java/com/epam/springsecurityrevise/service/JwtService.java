package com.epam.springsecurityrevise.service;

import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) throws UserNotFoundException, TokenTypeNotFoundException;

    String generateRefreshToken(UserDetails userDetails) throws
            UserNotFoundException,
            TokenTypeNotFoundException;

    String generateToken(UserDetails userDetails) throws UserNotFoundException, TokenTypeNotFoundException;

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isRefreshTokenValid(String token, UserDetails userDetails);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);
}
