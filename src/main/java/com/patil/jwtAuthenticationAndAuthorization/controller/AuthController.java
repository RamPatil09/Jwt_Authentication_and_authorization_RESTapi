package com.patil.jwtAuthenticationAndAuthorization.controller;

import com.patil.jwtAuthenticationAndAuthorization.dto.LoginRequest;
import com.patil.jwtAuthenticationAndAuthorization.dto.LoginResponse;
import com.patil.jwtAuthenticationAndAuthorization.dto.RegisterRequest;
import com.patil.jwtAuthenticationAndAuthorization.security.jwt.JwtUtil;
import com.patil.jwtAuthenticationAndAuthorization.security.UserDetailsImpl;
import com.patil.jwtAuthenticationAndAuthorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsImpl userDetails;
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return userService.save(request);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails1 = userDetails.loadUserByUsername(request.getEmail());
        String token = this.jwtUtil.generateToken(userDetails1);

        LoginResponse loginResponse = new LoginResponse(userDetails1.getUsername(), token);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Invalid credentials";
    }

}
