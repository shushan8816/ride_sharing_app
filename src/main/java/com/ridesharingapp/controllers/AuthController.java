package com.ridesharingapp.controllers;

import com.ridesharingapp.dto.requests.AuthenticationRequest;
import com.ridesharingapp.dto.requests.LogoutRequest;
import com.ridesharingapp.dto.requests.RegisterRequest;
import com.ridesharingapp.models.User;
import com.ridesharingapp.services.interfaces.AuthService;
import com.ridesharingapp.utils.exceptions.DuplicateDataException;
import com.ridesharingapp.utils.exceptions.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request) throws JwtAuthenticationException {
        return ResponseEntity.ok(authService.login(request));

    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) throws DuplicateDataException {
        authService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.loggedOut(logoutRequest);
        return ResponseEntity.ok().build();
    }
}
