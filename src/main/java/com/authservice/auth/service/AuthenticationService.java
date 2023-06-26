package com.authservice.auth.service;

import com.authservice.auth.config.JwtService;
import com.authservice.auth.dao.AuthRequest;
import com.authservice.auth.dao.AuthResponse;
import com.authservice.auth.dao.Credentials;
import com.authservice.auth.exception.UserNameNotFoundException;
import com.authservice.auth.model.ForgotPassword;
import com.authservice.auth.model.Roles;
import com.authservice.auth.model.UserData;
import com.authservice.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {


    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;


    private AuthenticationManager authenticationManager;

    private KafkaTemplate<Long,String> kafkaTemplate;



    public AuthResponse registerUser(AuthRequest authRequest)
    {
        long count= userRepository.count()+1;
        var userData=UserData.builder()
                .id(count)
                .name(authRequest.getName())
                .email(authRequest.getEmail())
                .age(authRequest.getAge())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Roles.USER)
                .build();
        userRepository.save(userData);
        var jwtToken=jwtService.generateToken(userData);
        kafkaTemplate.send("register-topic",count,authRequest.getName());
        log.info("User Registered successfully");

        return AuthResponse.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .age(userData.getAge())
                .password(userData.getPassword())
                .token(jwtToken)
                .build();
    }

    public String authenticateUser(Credentials credentials)
    {
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                        credentials.getUserName(),
                credentials.getPassword()
        ));

        var userData=userRepository.findByEmail(credentials.getUserName())
                .orElseThrow();
//        var jwtToken=jwtService.generateToken(userData);


        return jwtService.generateToken(userData);
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

    public Optional<UserData> updatePassword(ForgotPassword forgotPassword) throws UserNameNotFoundException {
        Optional<UserData> userData=userRepository.findByEmail(forgotPassword.getUserName());
        if(userData.isEmpty())
        {
            throw  new UserNameNotFoundException("User not Found");
        }
        userData.get().setPassword(passwordEncoder.encode(forgotPassword.getNewPassword()));
        userRepository.save(userData.get());

        return userData;

    }
    public Optional<UserData> getUserByName(String name) throws UserNameNotFoundException {
        Optional<UserData> userData=userRepository.findByEmail(name);
        if(userData.isEmpty())
        {
            throw new UserNameNotFoundException("User Not found");
        }
        return userData;
    }


}
