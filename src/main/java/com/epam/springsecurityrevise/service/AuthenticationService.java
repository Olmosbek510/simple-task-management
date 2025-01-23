package com.epam.springsecurityrevise.service;

import com.epam.springsecurityrevise.dto.request.AuthenticateRequestDTO;
import com.epam.springsecurityrevise.dto.request.RegisterRequestDto;
import com.epam.springsecurityrevise.dto.response.AuthRegisterResponseDTO;
import com.epam.springsecurityrevise.dto.response.AuthenticationResponseDTO;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface AuthenticationService {
    CompletableFuture<AuthRegisterResponseDTO> register(RegisterRequestDto registerRequest) throws UsernameAlreadyTakenException, RoleNotFoundException;

    AuthenticationResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO) throws UserNotFoundException, TokenTypeNotFoundException;

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws UserNotFoundException, TokenTypeNotFoundException, IOException;
}
