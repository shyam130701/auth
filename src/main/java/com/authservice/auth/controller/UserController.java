package com.authservice.auth.controller;

import com.authservice.auth.model.UserData;
import com.authservice.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo")
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
}
