package com.authservice.auth.controller;

import com.authservice.auth.config.JwtService;
import com.authservice.auth.dao.AuthRequest;
import com.authservice.auth.dao.AuthResponse;
import com.authservice.auth.dao.Credentials;
import com.authservice.auth.exception.UserNameNotFoundException;
import com.authservice.auth.model.ForgotPassword;
import com.authservice.auth.model.UserData;
import com.authservice.auth.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {


    private AuthenticationService authenticationService;


    private JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest)
    {
        AuthResponse authResponse=authenticationService.registerUser(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credentials credentials)
    {
        String authResponse=authenticationService.authenticateUser(credentials);
        return  new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Optional<UserData>> update(@RequestBody ForgotPassword forgotPassword) throws UserNameNotFoundException {
        Optional<UserData> userData=authenticationService.updatePassword(forgotPassword);
        return new ResponseEntity<>(userData,HttpStatus.CREATED);
    }



    @GetMapping("/getUser")
    @SneakyThrows
    public Optional<UserData> userData(@RequestParam("userName") String name)
    {
        return authenticationService.getUserByName(name);
    }



}
