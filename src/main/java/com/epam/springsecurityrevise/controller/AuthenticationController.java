package com.epam.springsecurityrevise.controller;

import com.epam.springsecurityrevise.dto.request.AuthenticateRequestDTO;
import com.epam.springsecurityrevise.dto.request.RegisterRequestDto;
import com.epam.springsecurityrevise.dto.response.AuthRegisterResponseDTO;
import com.epam.springsecurityrevise.dto.response.AuthenticateResponseDTO;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

import static com.epam.springsecurityrevise.constants.Uri.Auth.*;
import static com.epam.springsecurityrevise.util.HttpUtil.buildResponse;


@RestController
@RequestMapping(BASE_URI)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(REGISTER)
    public ResponseEntity<AuthRegisterResponseDTO> register(
            @RequestBody @Valid RegisterRequestDto registerRequest) throws
            UsernameAlreadyTakenException, RoleNotFoundException {

        AuthRegisterResponseDTO registerDto = authenticationService.register(registerRequest);

        return buildResponse(registerDto, HttpStatus.CREATED);
    }

    @PostMapping(AUTHENTICATE)
    public ResponseEntity<AuthenticateResponseDTO> authenticate(
            @RequestBody @Valid AuthenticateRequestDTO authenticateRequestDTO
    ) throws UserNotFoundException, TokenTypeNotFoundException {
        AuthenticateResponseDTO authResponseDto =
                authenticationService.authenticate(authenticateRequestDTO);

        return buildResponse(authResponseDto, HttpStatus.OK);
    }

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
}
