package com.authservice.auth.controller;

import com.authservice.auth.config.JwtAuthenticationFilter;
import com.authservice.auth.config.JwtService;
import com.authservice.auth.dao.AuthRequest;
import com.authservice.auth.dao.AuthResponse;
import com.authservice.auth.model.Roles;
import com.authservice.auth.model.UserData;
import com.authservice.auth.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {
    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @InjectMocks
    private AuthenticationController authenticationController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
        authenticationController=new AuthenticationController(authenticationService,jwtService);
    }


    @Test
    void register() throws Exception {
        UserData userData=new UserData(1L,"Shyam",21,"shyam@gmail.com","Shyam", Roles.USER);
        AuthRequest authRequest=new AuthRequest("Shyam",21,"shyam@gmail.com","Shyam");
        AuthResponse authResponse=new AuthResponse("Shyam",21,"shyam@gmail.com","Shyam"
                ,"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHlhbUBnbWFpbC5jb20iLCJpYXQiOjE2ODEzNjg3MDgsImV4cCI6MTY4MTM3MDE0OH0.HtKe8LmrZRtFnf4GBAI6yoZ1Nn6acuGxngfuDT17-Y0");

        Mockito.when(authenticationService.registerUser(authRequest)).thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest))

        ).andExpect(status().isCreated());
    }
}