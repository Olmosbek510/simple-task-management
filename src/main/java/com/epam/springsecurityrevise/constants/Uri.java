package com.epam.springsecurityrevise.constants;

public interface Uri {
    interface Admin {
        String BASE_URI = "/api/v1/admin";
    }

    interface Auth {
        String BASE_URI = "/api/v1/auth";
        String AUTHENTICATE = "/authenticate";
        String REGISTER = "/register";
        String LOGOUT = "/logout";
        String REFRESH = "/refresh";
    }
}
