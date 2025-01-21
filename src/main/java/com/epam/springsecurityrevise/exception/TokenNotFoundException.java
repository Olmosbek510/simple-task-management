package com.epam.springsecurityrevise.exception;

public class TokenNotFoundException extends Throwable {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
