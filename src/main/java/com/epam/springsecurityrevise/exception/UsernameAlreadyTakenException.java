package com.epam.springsecurityrevise.exception;

public class UsernameAlreadyTakenException extends Throwable {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
