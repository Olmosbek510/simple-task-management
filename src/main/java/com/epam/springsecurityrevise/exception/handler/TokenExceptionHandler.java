package com.epam.springsecurityrevise.exception.handler;

import com.epam.springsecurityrevise.dto.response.ErrorResponse;
import com.epam.springsecurityrevise.exception.TokenTypeNotFoundException;
import com.epam.springsecurityrevise.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionHandler {
    @ExceptionHandler(TokenTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTokenTypeNotFoundException(
            TokenTypeNotFoundException exception
    ) {
        return HttpUtil.respondWithError(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
