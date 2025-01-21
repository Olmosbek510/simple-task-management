package com.epam.springsecurityrevise.constants;

import java.util.List;

public interface SecurityConstants {
    String AUTH_HEADER = "Authorization";

    String BEARER = "Bearer ";

    List<String> WHITE_LIST = List.of(
            Uri.Auth.BASE_URI + "/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**"
    );
}
