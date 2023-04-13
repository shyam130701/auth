package com.authservice.auth.controller;

import com.authservice.auth.dao.AuthRequest;
import com.authservice.auth.dao.AuthResponse;
import com.authservice.auth.dao.Credentials;
import com.authservice.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.AuthorizeRequestsDsl;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest)
    {
        AuthResponse authResponse=authenticationService.registerUser(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Credentials credentials)
    {
        AuthResponse authResponse=authenticationService.authenticateUser(credentials);
        return  new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }
}
