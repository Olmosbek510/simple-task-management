package com.epam.springsecurityrevise.config.security;

import com.epam.springsecurityrevise.constants.SecurityConstants;
import com.epam.springsecurityrevise.constants.Uri;
import com.epam.springsecurityrevise.service.impl.LogoutServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutServiceImpl logoutService;

    @Value("${security.csrf.disable}")
    private boolean csrfDisable;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if (csrfDisable)
            http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth ->
                SecurityConstants.WHITE_LIST.forEach(uri ->
                        auth.requestMatchers(uri).permitAll())
        );

        http.authorizeHttpRequests(auth ->
                auth.anyRequest().authenticated());

        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.addLogoutHandler(logoutService)
                .logoutUrl(Uri.Auth.BASE_URI + Uri.Auth.LOGOUT)
                .logoutSuccessHandler(((
                        request,
                        response,
                        authentication) -> SecurityContextHolder.clearContext())));


        http.authenticationProvider(authenticationProvider);

        http.sessionManagement(m ->
                m.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }
}
