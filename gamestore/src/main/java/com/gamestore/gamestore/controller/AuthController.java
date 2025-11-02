package com.gamestore.gamestore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.gamestore.gamestore.dto.CreateUserDTO;
import com.gamestore.gamestore.dto.LoginUserDTO;

import com.gamestore.gamestore.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthService authservice;

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> CreateUserRequest(@Valid @RequestBody CreateUserDTO userRequest){
        return ResponseEntity.ok().body(authservice.CreateUser(userRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> LoginUserRequest(@Valid @RequestBody LoginUserDTO userRequest){
        return ResponseEntity.ok().body(authservice.Authentication(userRequest));
    }
    
    @PostMapping("/login_token")
    public ResponseEntity<Map<String,Object>> VerifyJwtToken(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(authservice.LoginJwtToken(userDetails));
    }
}
