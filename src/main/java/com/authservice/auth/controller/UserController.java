package com.authservice.auth.controller;

import com.authservice.auth.model.UserData;
import com.authservice.auth.service.AuthenticationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value = "/add")
    public String getting()
    {
        return "Shyam";
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserData>> getAll()
    {
        List<UserData> userData=authenticationService.userDataList();
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String deletingAll(@PathVariable long id)
    {
        return authenticationService.deleteAllUser(id);
    }

    @GetMapping("/getUser")
    @SneakyThrows
    public Optional<UserData> userData(@RequestParam("userName") String name)
    {
        return authenticationService.getUserByName(name);
    }
}
