package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.constants.SecurityConstants;
import com.epam.springsecurityrevise.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutServiceImpl implements LogoutHandler {
    private final TokenService tokenService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        final String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER);
        final String jwt;

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER)) {
            return;
        }

        jwt = authHeader.substring(7);

        tokenService.revokeToken(jwt);
    }
}
