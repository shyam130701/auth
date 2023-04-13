package com.authservice.auth.service;

import com.authservice.auth.config.JwtService;
import com.authservice.auth.dao.AuthRequest;
import com.authservice.auth.dao.AuthResponse;
import com.authservice.auth.dao.Credentials;
import com.authservice.auth.model.Roles;
import com.authservice.auth.model.UserData;
import com.authservice.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;



    public AuthResponse registerUser(AuthRequest authRequest)
    {
        var userData=UserData.builder()
                .name(authRequest.getName())
                .email(authRequest.getEmail())
                .age(authRequest.getAge())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Roles.USER)
                .build();
        userRepository.save(userData);
        var jwtToken=jwtService.generateToken(userData);

        return AuthResponse.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .age(userData.getAge())
                .password(userData.getPassword())
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticateUser(Credentials credentials)
    {
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                        credentials.getUserName(),
                credentials.getPassword()
        ));

        var userData=userRepository.findByEmail(credentials.getUserName())
                .orElseThrow();
        var jwtToken=jwtService.generateToken(userData);

        return AuthResponse.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .age(userData.getAge())
                .password(userData.getPassword())
                .token(jwtToken)
                .build();
    }


    public List<UserData> userDataList()
    {
         return userRepository.findAll()
                 .stream()
                 .toList();
    }
    public String deleteAllUser(Long id)
    {
        userRepository.deleteById(id);
        return "Deleted";
    }


}
