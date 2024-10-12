package com.example.Backend_ApprenticeshipProgram.controller;

import com.example.Backend_ApprenticeshipProgram.dto.AuthDto;
import com.example.Backend_ApprenticeshipProgram.dto.UserDto;
import com.example.Backend_ApprenticeshipProgram.service.JWTService;
import com.example.Backend_ApprenticeshipProgram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService,
                          UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody @Validated AuthDto authDto) {
        try {
            authenticateUser(authDto);
            String token = generateToken(authDto.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Authentication successful");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Validated UserDto userDto) {
        String message = userService.registerUser(userDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("status", "User registered successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    private void authenticateUser(AuthDto authDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword())
        );
    }

    private String generateToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails.getUsername());
    }
}
