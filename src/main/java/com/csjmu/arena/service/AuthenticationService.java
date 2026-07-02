package com.csjmu.arena.service;

import com.csjmu.arena.dto.LoginRequest;
import com.csjmu.arena.dto.LoginResponse;
import com.csjmu.arena.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.csjmu.arena.security.CustomUserDetailsService;
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 CustomUserDetailsService customUserDetailsService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),
                        request.getPassword()

                )

        );

        UserDetails user = customUserDetailsService
                .loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .build();

    }

}