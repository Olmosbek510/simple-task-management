package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.constants.SecurityConstants;
import com.epam.springsecurityrevise.dto.request.AuthenticateRequestDTO;
import com.epam.springsecurityrevise.dto.request.RegisterRequestDto;
import com.epam.springsecurityrevise.dto.response.AuthRegisterResponseDTO;
import com.epam.springsecurityrevise.dto.response.AuthenticationResponseDTO;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.mapper.UserMapper;
import com.epam.springsecurityrevise.model.Role;
import com.epam.springsecurityrevise.model.User;
import com.epam.springsecurityrevise.model.enums.RoleName;
import com.epam.springsecurityrevise.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    @Async
    public CompletableFuture<AuthRegisterResponseDTO> register(RegisterRequestDto registerRequest) throws UsernameAlreadyTakenException, RoleNotFoundException {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new UsernameAlreadyTakenException("Username already taken. Cannot register");
        }
        AuthRegisterResponseDTO registerResponseDto = AuthRegisterResponseDTO.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();

        Role role = roleService.findByName(RoleName.USER);

        User user = userMapper.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(role));

        userService.save(user);

        return CompletableFuture.completedFuture(registerResponseDto);
    }

    @Override
    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO) throws UserNotFoundException, TokenTypeNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequestDTO.getEmail(),
                        authenticateRequestDTO.getPassword()
                )
        );

        User user = userService.findByEmail(authenticateRequestDTO.getEmail());

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws UserNotFoundException, TokenTypeNotFoundException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER)) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            User user = userService.findByEmail(userEmail);
            if (jwtService.isRefreshTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);


                var responseBody = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.writeValue(response.getOutputStream(), responseBody);
            }
        }
    }
}
