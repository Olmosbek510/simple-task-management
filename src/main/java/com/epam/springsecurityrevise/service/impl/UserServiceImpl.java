package com.epam.springsecurityrevise.service.impl;

import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.model.User;
import com.epam.springsecurityrevise.repository.UserRepository;
import com.epam.springsecurityrevise.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User '%s' not found.");
        }
        return optionalUser.get();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public User save(User user) throws UsernameAlreadyTakenException {
        if (existsByEmail(user.getEmail())) {
            throw new UsernameAlreadyTakenException("Username already taken.");
        }
        return userRepository.save(user);
    }
}
