package com.epam.springsecurityrevise.service;

import com.epam.springsecurityrevise.dto.request.AuthenticateRequestDTO;
import com.epam.springsecurityrevise.dto.request.RegisterRequestDto;
import com.epam.springsecurityrevise.dto.response.AuthRegisterResponseDTO;
import com.epam.springsecurityrevise.dto.response.AuthenticateResponseDTO;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;

import javax.management.relation.RoleNotFoundException;

public interface AuthenticationService {
    AuthRegisterResponseDTO register(RegisterRequestDto registerRequest) throws UsernameAlreadyTakenException, RoleNotFoundException;

    AuthenticateResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO) throws UserNotFoundException, TokenTypeNotFoundException;
}
