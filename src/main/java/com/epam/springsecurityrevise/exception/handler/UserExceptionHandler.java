package com.epam.springsecurityrevise.exception.handler;

import com.epam.springsecurityrevise.dto.response.ErrorResponse;
import com.epam.springsecurityrevise.exception.UsernameAlreadyTakenException;
import com.epam.springsecurityrevise.exception.UserNotFoundException;
import com.epam.springsecurityrevise.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException exception) {
        return HttpUtil.respondWithError(
                HttpStatus.NOT_FOUND,
                exception.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyTakenException(
            UsernameAlreadyTakenException exception
    ) {
        return HttpUtil.respondWithError(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }
}
