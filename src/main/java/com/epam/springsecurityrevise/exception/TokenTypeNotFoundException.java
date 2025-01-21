package com.epam.springsecurityrevise.exception;

public class TokenTypeNotFoundException extends Throwable{
    public TokenTypeNotFoundException(String message) {
        super(message);
    }
}
