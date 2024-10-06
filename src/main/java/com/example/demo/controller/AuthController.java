package com.example.demo.controller;

import com.example.demo.dto.RegisterDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Handler to register a user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        return userService.saveUser(registerDto);
    }

    // Handler to login a user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RegisterDto registerDto) {
        return userService.login(registerDto);
    }

    
}
