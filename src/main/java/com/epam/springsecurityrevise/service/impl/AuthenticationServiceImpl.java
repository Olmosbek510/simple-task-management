package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.dto.request.AuthenticateRequestDTO;
import com.epam.springsecurityrevise.dto.request.RegisterRequestDto;
import com.epam.springsecurityrevise.dto.response.AuthRegisterResponseDTO;
import com.epam.springsecurityrevise.dto.response.AuthenticateResponseDTO;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.mapper.UserMapper;
import com.epam.springsecurityrevise.model.Role;
import com.epam.springsecurityrevise.model.User;
import com.epam.springsecurityrevise.model.enums.RoleName;
import com.epam.springsecurityrevise.service.AuthenticationService;
import com.epam.springsecurityrevise.service.JwtService;
import com.epam.springsecurityrevise.service.RoleService;
import com.epam.springsecurityrevise.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.Set;

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

    @Override
    public AuthRegisterResponseDTO register(RegisterRequestDto registerRequest) throws UsernameAlreadyTakenException, RoleNotFoundException {
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

        return registerResponseDto;
    }

    @Override
    @Transactional
    public AuthenticateResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO) throws UserNotFoundException, TokenTypeNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequestDTO.getEmail(),
                        authenticateRequestDTO.getPassword()
                )
        );

        User user = userService.findByEmail(authenticateRequestDTO.getEmail());
        String jwtToken = jwtService.generateToken(user);

        return AuthenticateResponseDTO.builder()
                .token(jwtToken)
                .build();
    }
}
