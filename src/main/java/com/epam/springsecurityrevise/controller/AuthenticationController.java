package com.epam.springsecurityrevise.controller;

import com.epam.springsecurityrevise.dto.request.AuthenticateRequestDTO;
import com.epam.springsecurityrevise.dto.request.RegisterRequestDto;
import com.epam.springsecurityrevise.dto.response.AuthRegisterResponseDTO;
import com.epam.springsecurityrevise.dto.response.AuthenticationResponseDTO;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.epam.springsecurityrevise.constants.Uri.Auth.*;
import static com.epam.springsecurityrevise.util.HttpUtil.buildResponse;


@Slf4j
@RestController
@RequestMapping(BASE_URI)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(REGISTER)
    @Async
    public CompletableFuture<ResponseEntity<AuthRegisterResponseDTO>> register(
            @RequestBody @Valid RegisterRequestDto registerRequest) throws
            UsernameAlreadyTakenException, RoleNotFoundException, ExecutionException, InterruptedException {

        long l = System.currentTimeMillis();

        CompletableFuture<AuthRegisterResponseDTO> register = authenticationService.register(registerRequest);
        var registerDto = register.get();

        log.info("time spent: {}", System.currentTimeMillis() - l);

        return CompletableFuture.completedFuture(buildResponse(registerDto, HttpStatus.CREATED));
    }

    @PostMapping(AUTHENTICATE)
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody @Valid AuthenticateRequestDTO authenticateRequestDTO
    ) throws UserNotFoundException, TokenTypeNotFoundException {
        AuthenticationResponseDTO authResponseDto =
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

    @PostMapping(REFRESH)
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws UserNotFoundException, IOException, TokenTypeNotFoundException {
        authenticationService.refreshToken(request, response);
    }
}
