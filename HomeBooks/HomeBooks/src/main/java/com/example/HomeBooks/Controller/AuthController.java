package com.example.HomeBooks.Controller;

import com.example.HomeBooks.Model.User;
import com.example.HomeBooks.Service.UserService;

import com.example.HomeBooks.dto.LoginRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user){

        User savedUser = userService.registerUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestBody LoginRequest loginRequest
    ) {

        String response =
                userService.loginUser(loginRequest);

        return ResponseEntity.ok(response);
    }
}