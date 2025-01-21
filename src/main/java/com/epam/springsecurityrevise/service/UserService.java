package com.epam.springsecurityrevise.service;

import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.model.User;
import jakarta.validation.constraints.NotBlank;

public interface UserService {
    User findByEmail(String username) throws UserNotFoundException;

    boolean existsByEmail(@NotBlank(message = "Email name cannot be blank") String email);

    User save(User user) throws UsernameAlreadyTakenException;
}
