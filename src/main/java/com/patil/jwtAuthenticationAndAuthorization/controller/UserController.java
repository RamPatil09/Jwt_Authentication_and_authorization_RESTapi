package com.patil.jwtAuthenticationAndAuthorization.controller;

import com.patil.jwtAuthenticationAndAuthorization.dto.RegisterRequest;
import com.patil.jwtAuthenticationAndAuthorization.exception.UserNotFoundException;
import com.patil.jwtAuthenticationAndAuthorization.model.User;
import com.patil.jwtAuthenticationAndAuthorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hello World!");
    }


    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteByEmail(@PathVariable String email) {
        return userService.deleteByEmail(email);
    }

    @GetMapping("/getByEmail/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> findAll() {
        List<User> all = userService.findAll();
        return ResponseEntity.ok(all);
    }
}
