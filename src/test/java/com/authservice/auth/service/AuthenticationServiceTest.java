package com.authservice.auth.service;

import com.authservice.auth.config.JwtService;
import com.authservice.auth.dao.AuthRequest;
import com.authservice.auth.dao.AuthResponse;
import com.authservice.auth.dao.Credentials;
import com.authservice.auth.model.Roles;
import com.authservice.auth.model.UserData;
import com.authservice.auth.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class AuthenticationServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtService jwtService;

    @BeforeEach
    void setUp()
    {
        authenticationService = new AuthenticationService(userRepository,passwordEncoder,jwtService,authenticationManager);
    }


    @Test
    void registerUser() {
        UserData userData=new UserData(1L,"Shyam",21,"shyam@gmail.com","Shyam", Roles.USER);
        AuthRequest authRequest=new AuthRequest("Shyam",21,"shyam@gmail.com","Shyam");
        AuthResponse authResponse=new AuthResponse("Shyam",21,"shyam@gmail.com","Shyam"
                ,"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHlhbUBnbWFpbC5jb20iLCJpYXQiOjE2ODEzNjg3MDgsImV4cCI6MTY4MTM3MDE0OH0.HtKe8LmrZRtFnf4GBAI6yoZ1Nn6acuGxngfuDT17-Y0");
        authenticationService.registerUser(authRequest);
        ArgumentCaptor<UserData> userDataArgumentCaptor=ArgumentCaptor.forClass(UserData.class);
        Mockito.verify(userRepository).save(userDataArgumentCaptor.capture());
        Assertions.assertThat(userDataArgumentCaptor.getValue().getName()).isSameAs(authResponse.getName());
    }

    @Test
    void authenticateUser() {
        Credentials credentials
                =new Credentials("shyam@gmail.com","Shyam");
        var token="token";

        UserData userData=new UserData(1L,"Shyam",21,"shyam@gmail.com","Shyam", Roles.USER);
        AuthResponse authResponse1=new AuthResponse("Shyam",21,"shyam@gmail.com","Shyam"
                ,"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHlhbUBnbWFpbC5jb20iLCJpYXQiOjE2ODEzNjg3MDgsImV4cCI6MTY4MTM3MDE0OH0.HtKe8LmrZRtFnf4GBAI6yoZ1Nn6acuGxngfuDT17-Y0");





    }
}