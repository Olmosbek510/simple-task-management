package com.epam.springsecurityrevise.controller;

import com.epam.springsecurityrevise.dto.request.AuthenticateRequestDTO;
import com.epam.springsecurityrevise.dto.request.RegisterRequestDto;
import com.epam.springsecurityrevise.dto.response.AuthRegisterResponseDTO;
import com.epam.springsecurityrevise.dto.response.AuthenticateResponseDTO;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

import static com.epam.springsecurityrevise.constants.Uri.Auth.*;


@RestController
@RequestMapping(BASE_URI)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(REGISTER)
    public ResponseEntity<AuthRegisterResponseDTO> register(
            @RequestBody RegisterRequestDto registerRequest) throws
            UsernameAlreadyTakenException, RoleNotFoundException {
        AuthRegisterResponseDTO registerDto = authenticationService.register(registerRequest);
        return ResponseEntity.ok(registerDto);
    }

    @PostMapping(AUTHENTICATE)
    public ResponseEntity<AuthenticateResponseDTO> authenticate(
            @RequestBody AuthenticateRequestDTO authenticateRequestDTO
    ) throws UserNotFoundException {
        AuthenticateResponseDTO authResponseDto =
                authenticationService.authenticate(authenticateRequestDTO);

        return ResponseEntity.ok(authResponseDto);
    }
}
